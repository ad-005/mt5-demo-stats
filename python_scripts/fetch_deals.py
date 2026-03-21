import MetaTrader5 as mt5
import json
import sys
import pandas as pd
from datetime import datetime

if not mt5.initialize():
    print(json.dumps({"error": "initialization failed", "code": mt5.last_error()}))
    sys.exit()

mt5.login()

# Get account login for the trades
account_info = mt5.account_info()
account_login = str(account_info.login) if account_info else ""

deals = mt5.history_deals_get(datetime(2026, 1, 20), datetime.now())

if deals is None:
    print(json.dumps({"error": "no deals found"}))
else:
    df = pd.DataFrame(list(deals), columns=deals[0]._asdict().keys())
    df['time'] = pd.to_datetime(df['time'], unit='s')

    # Filter out balance operations (position_id = 0)
    df = df[df['position_id'] != 0]

    if df.empty:
        print(json.dumps({"error": "no trades found"}))
    else:
        trades = df.groupby('position_id').agg({
            'symbol': 'first',
            'type': 'first',
            'volume': 'first',
            'price': ['first', 'last'],
            'profit': 'sum',
            'commission': 'sum',
            'swap': 'sum',
            'time': ['first', 'last']
        }).reset_index()

        trades.columns = ['ticket', 'symbol', 'type', 'volume',
                          'openPrice', 'closePrice', 'profit',
                          'commission', 'swap', 'openTime', 'closeTime']

        # SL/TP not available in deals, set to 0.0
        trades['stopLoss'] = 0.0
        trades['takeProfit'] = 0.0

        # Convert type from numeric to string (0=BUY, 1=SELL)
        trades['type'] = trades['type'].apply(lambda x: 'BUY' if x == 0 else 'SELL')

        # Convert ticket to string
        trades['ticket'] = trades['ticket'].astype(str)

        # Calculate change percentage: (closePrice - openPrice) / openPrice * 100
        # For SELL trades, invert the calculation
        def calc_change(row):
            if row['openPrice'] == 0:
                return 0.0
            change = (row['closePrice'] - row['openPrice']) / row['openPrice'] * 100
            if row['type'] == 'SELL':
                change = -change
            return round(change, 4)

        trades['change'] = trades.apply(calc_change, axis=1)

        # Add account login
        trades['accountLogin'] = account_login

        # Format datetime to ISO format string (compatible with Java LocalDateTime)
        trades['openTime'] = trades['openTime'].dt.strftime('%Y-%m-%dT%H:%M:%S')
        trades['closeTime'] = trades['closeTime'].dt.strftime('%Y-%m-%dT%H:%M:%S')

        # Select and order columns to match Java Trade class
        output_columns = ['openTime', 'symbol', 'ticket', 'type', 'volume',
                          'openPrice', 'stopLoss', 'takeProfit', 'closeTime',
                          'closePrice', 'profit', 'commission', 'swap', 'change', 'accountLogin']
        trades = trades[output_columns]

        data = trades.to_json(orient='records')
        for trade in json.loads(data):
            print(json.dumps(trade))

mt5.shutdown()
