public class WiFiDemo extends Activity implements OnClickListener
{
    WifiManager wifi;
    ListView    lv;
    TextView    textStatus;
    Button      buttonScan;
    int         size  = 0;
    List<ScanResult>  results;

    String ITEM_KEY = "key";
    ArrayList<Hashmap<String, String>> arraylist = new ArrayList<Hashmap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);

      textStatus  = (TextView)  findViewById(R.id.textStatus);
      buttonScan  = (Button)    findViewById(R.id.buttonScan);
      buttonScan.setOnClickListener(this);
      lv  = (ListView)findViewById(R.id.list);

      wifi  = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
      if (wifi.isWifiEnabled() == false)
      {
        Toast.makeText(getApplicationContext(), ":::  Wi-Fi is DISABLED ::: \n :::  Made Wi-Fi ABLE again :::", Toast.LENTH_LONG).show();
        wifi.setWifiEnabled(true);
      }
      this.adapter  = new SimpleAdapter(WiFiDemo.this,  arraylist,  R.layout.row, new String[]  {ITEM_KEY}, new int[] { R.id.list_value}  );
      lv.setAdapter(this.adapter);

      registerReceiver(new BroadcastReceiver()
        {
          @Override
          public void onReceive(Context c , Intent intent)
            {
              results = wifi.getScanResults();
              size    = results.size();
            }
        },  new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) );

    }

  public void onClick(View view)
  {
    arraylist.clear();
    wifi.startScan();

    Toast.makeText(this, " Scanning... " +  size, Toast.LENGTH_SHORT).show();
    try   // YOU CAN DO IT ,DONT JUST TRY
    {
      size  = size  -1;
      while(size >= 0)
        {
          HashMap<String, String> item  = new HashMap<String, String>();
          item.put(ITEM_KEY, results.get(size).SSID + " " + results.get(size).capabillities);

          arraylist.add(item);
          size--;
          adapter.notifyDataSetChanged();
        }

    }
    catch (Exception e)
      { }
  }
}
