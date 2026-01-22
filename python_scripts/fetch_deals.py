import MetaTrader5 as mt5
import json
import sys
import pandas as pd
from datetime import datetime

if not mt5.initialize():
    print(json.dumps({"error": "initialization failed", "code": mt5.last_error()}))
    sys.exit()

mt5.login()
deals = mt5.history_deals_get(datetime(2026, 1, 20), datetime.now())

if deals is None:
    print(json.dumps({"error": "no deals found"}))
else:
    df = pd.DataFrame(list(deals))
    data = df.to_dict(orient='records')

    print(json.dumps(data))

mt5.shutdown()