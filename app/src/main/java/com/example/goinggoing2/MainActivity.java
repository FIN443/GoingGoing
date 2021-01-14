package com.example.goinggoing2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity {
    public static Context context_main;
    private TextView tv_name;
    private Button btn_remake,btn_check,btn_location,btn_emergency;
    private Button btnConnect; // 블루투스 연결버튼
    private Switch sw1;
    public BluetoothSPP bt;

    private BarChart barChart;
    private LineChart lineChart;
    int[] data = new int[12];

    public static String info_name;
    public static String info_age;
    public static String info_gender;
    public static String info_height;
    public static String info_weight;
    private int info_bpm = 0;

    Handler mhandler = new Handler();
    MyRunnable runnable = new MyRunnable();
    boolean mIsRunning = false;
    long prevtime;

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            if(SystemClock.elapsedRealtime() - prevtime > 15000) { // 15초마다
                // 실행문
                updateData();
                setBarChart();
                setLineChart();
                prevtime = SystemClock.elapsedRealtime();
            }
            mhandler.post(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context_main = this;
        bt = new BluetoothSPP(this); // initializing
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        tv_name=findViewById(R.id.textView_name);
        btn_remake=findViewById(R.id.remake_button);
        btn_check=findViewById(R.id.button_check);
        btn_location=findViewById(R.id.button_location);
        btn_emergency=findViewById(R.id.button_emergency);
        sw1 = findViewById(R.id.switch1);

        barChart = (BarChart) findViewById(R.id.home_barChart);
        lineChart = (LineChart) findViewById(R.id.lineChart);

        btnConnect = findViewById(R.id.btnConnect);


        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        // 쓰레드
        if (mIsRunning == false) {
            loadData();
            setBarChart();
            setLineChart();
            mIsRunning = true;
            prevtime = SystemClock.elapsedRealtime();
            mhandler.post(runnable);
        }

        if(!sw1.isChecked()) {
            barChart.setVisibility(View.GONE);
            lineChart.setVisibility(View.VISIBLE);
            sw1.setText("LineChart");
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                info_bpm = Integer.parseInt(message);
                tv_name.setText(message);
            }
        });

        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceConnected(String name, String address) { //연결됐을 때
                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() { // 블루투스 연결 버튼
            public void onClick(View v) {
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });

        btn_remake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog dialog = new CustomDialog(MainActivity.this);
                dialog.setDialogListener(new CustomDialog.CustomDialogListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveClicked(String name, String age, String gender, String height, String weight) {
                        info_name = name;
                        info_age = age;
                        info_gender = gender;
                        info_height = height;
                        info_weight = weight;
                        tv_name.setText(name);
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialog.show();
            }
        });

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    barChart.setVisibility(View.VISIBLE);
                    lineChart.setVisibility(View.GONE);
                    sw1.setText("BarChart");
                }
                else {
                    barChart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                    sw1.setText("LineChart");
                }
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = Integer.parseInt(info_age);
                int gender;
                if(info_gender.equals("여자")) {
                    gender = 2;
                }
                else {
                    gender = 1;
                }
                Intent intent =new Intent( getApplicationContext(),CheckActivity.class);
                intent.putExtra("나이", age);
                intent.putExtra("성별", gender);
                startActivity(intent);
            }
        });

        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/v5/entry/place/11591667?c=14184044.3197101,4349541.6049969,15,0,0,0,dh"));
                startActivity(myIntent);
            }
        });

        btn_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010"));
                startActivity(myIntent);
            }
        });
    }

    ArrayList<BarEntry> barEntryArrayList; //심박수
    ArrayList<String> labelsNames; //시간대
    ArrayList<ChartDateData> chartDateDataArrayList = new ArrayList<>(); //차트 데이터 관리용 (세트)

    private void setBarChart() {
        barEntryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        fillChartDate(); //차트 갱신

        //요 밑에 거 (---- 사이에) 꼭 필요함
        //-------
        for(int i = 0; i < chartDateDataArrayList.size(); i++) {
            String dates = chartDateDataArrayList.get(i).getDates();
            int scores = chartDateDataArrayList.get(i).getScores();
            barEntryArrayList.add(new BarEntry(i, scores));
            labelsNames.add(dates);
        }

        //데이터셋(차트모양) 설정
        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "BPM"); //y축 제목 (심박수)
        barDataSet.setColors(ColorTemplate.rgb("#f44336")); //막대 색깔 (기본값 랜덤으로 되있음)
        Description description = new Description();
        description.setText("Dates"); //x축 제목 (시간대)
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //x축 모양 및 간격 설정
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 표시 위치 (기본값은 하단)
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f); //막대 두께
//        xAxis.setLabelCount(labelsNames.size()); //x축 속성 크기
//        xAxis.setTextSize(0.8f);
//        xAxis.setLabelRotationAngle(270); //x축 속성 회전각도
        //y축 모양 및 간격 설정
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(200);
        rightAxis.setAxisMinimum(0);
        rightAxis.setAxisMaximum(200);

        barChart.invalidate();
        //-------
    }

    ArrayList<Entry> entryArrayList; //심박수

    private void setLineChart() {
        entryArrayList = new ArrayList<>();
        labelsNames = new ArrayList<>();
        fillChartDate(); //차트 갱신

        //요 밑에 거 (---- 사이에) 꼭 필요함
        //-------
        for(int i = 0; i < chartDateDataArrayList.size(); i++) {
            String dates = chartDateDataArrayList.get(i).getDates();
            int scores = chartDateDataArrayList.get(i).getScores();
            entryArrayList.add(new Entry(i, scores));
            labelsNames.add(dates);
        }

        //데이터셋(차트모양) 설정
        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "BPM"); //y축 제목 (심박수)
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        lineDataSet.setCircleColor(ColorTemplate.rgb("#f44336"));
        lineDataSet.setColor(ColorTemplate.rgb("#f44336"));
        Description description = new Description();
        description.setText("Dates"); //x축 제목 (시간대)
        lineChart.setDescription(description);
        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        //x축 모양 및 간격 설정
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsNames));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 표시 위치 (기본값은 하단)
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
//        xAxis.setLabelCount(labelsNames.size()); //x축 속성 크기
//        xAxis.setTextSize(0.8f);
//        xAxis.setLabelRotationAngle(270); //x축 속성 회전각도
        //y축 모양 및 간격 설정
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();
        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(200);
        rightAxis.setAxisMinimum(0);
        rightAxis.setAxisMaximum(200);

        lineChart.invalidate();
    }

    private void fillChartDate() { // 리스트에 데이터 넣기
        //요기 add 개수만큼 막대 개수
        chartDateDataArrayList.clear();
        chartDateDataArrayList.add(new ChartDateData("165초전", data[0]));
        chartDateDataArrayList.add(new ChartDateData("150초전", data[1]));
        chartDateDataArrayList.add(new ChartDateData("135초전", data[2]));
        chartDateDataArrayList.add(new ChartDateData("120초전", data[3]));
        chartDateDataArrayList.add(new ChartDateData("105초전", data[4]));
        chartDateDataArrayList.add(new ChartDateData("90초전", data[5]));
        chartDateDataArrayList.add(new ChartDateData("75초전", data[6]));
        chartDateDataArrayList.add(new ChartDateData("60초전", data[7]));
        chartDateDataArrayList.add(new ChartDateData("45초전", data[8]));
        chartDateDataArrayList.add(new ChartDateData("30초전", data[9]));
        chartDateDataArrayList.add(new ChartDateData("15초전", data[10]));
        chartDateDataArrayList.add(new ChartDateData("0초전", data[11]));
    }

    private void loadData() { // 불러오기(임의의 데이터 넣기)
        for(int i=0; i<data.length; i++) {
            data[i] = 0;
        }
    }

    private void updateData() { // 데이터 갱신
        for(int i=0; i<data.length; i++) {
            if(i==data.length-1) {
                data[i] = info_bpm; // 새로운 데이터 넣음
            }
            else {
                data[i] = data[i+1]; // 한칸 옮김
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mIsRunning == false) {
            loadData();
            setBarChart();
            setLineChart();
            mIsRunning = true;
            prevtime = SystemClock.elapsedRealtime();
            mhandler.post(runnable);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mhandler.removeCallbacks(runnable);
        mIsRunning = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
        mhandler.removeCallbacks(runnable);
        mIsRunning = false;
    }
}