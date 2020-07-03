package com.icandothisallday2020.ex58notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {
        //Notification(알림,공지) 관리자 객체 소환(운영체제 내 이미 존재)
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Notification 객체를 만들어주는 건축가 객체 생성
        NotificationCompat.Builder builder=null;
        //└OREO ver~(api ver.26~) '알림채널'이 생겼기 때문에 호환성 버전으로 생성
        //26버전 전후로 나누어서 코딩
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //알림채널 생성
            NotificationChannel channel=new NotificationChannel
                    ("ch01","I'm #Channel#",NotificationManager.IMPORTANCE_HIGH);
            //식별 id, 알림명, 중요도
            manager.createNotificationChannel(channel);//매니저에게 채널 전달
            builder=new NotificationCompat.Builder(this,"ch01");//notification 생성
            //parameter : context,channel(알림채널,VER OREO 전에는 채널 X==null)
        }else{
            builder=new NotificationCompat.Builder(this,null);
        }
        builder.setSmallIcon(R.drawable.ic_stat_name);
        //└상태표시줄에 보이는 아이콘 지정

        //확장상태바[상태표시줄을 드래그하여 아래로 내리면 보이는 알림창]에 보이는 설정
        builder.setContentTitle("This is a Notification");
        builder.setContentText("알림메세지 입니다.");
        builder.setSubText("tough cookie");

        Resources res=getResources();//res folder manager
        Bitmap bm= BitmapFactory.decodeResource(res,R.drawable.cookie);
        builder.setLargeIcon(bm);//확장된 상태바에 보여지는 그림-bitmap 으로

        //확장상태바에 큰 이미지 적용
        NotificationCompat.BigPictureStyle bps=new NotificationCompat.BigPictureStyle(builder);
        //파라미터로 빌더를 주면 자동으로 빌더와 연결
        bps.bigPicture(BitmapFactory.decodeResource(res,R.drawable.lesser));

        //여러 스타일 객체들
        //Notification.BigTextStyle//Notification.InboxStyle//Notification.MediaStyle

        builder.setProgress(100,50,true);
        //indeterminate :가늠할 수 없는 (다운로드 호라이즌 게이지바 무한 루프)

        //알림-진동//manifest 에서 퍼미션 추가
        builder.setVibrate(new long[]{0,2000,1000,3000});//0초대기, 2초진동,1초대기,3초진동

        //사운드
        //URI 객체 생성
        Uri androidUri= RingtoneManager.getActualDefaultRingtoneUri(this,RingtoneManager.TYPE_NOTIFICATION);
        //└안드로이드가 가지고 있는 기본 사운드
        //사용자 사운드
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.get_coin);
        //res 폴더 지칭
        builder.setSound(uri);

        //확장상태바의 알림을 클릭했을 때 새로운 액티비티로 이동
        Intent intent=new Intent(this,SecondActivity.class);
        //PendingIntent(보류중인 인텐트)생성-지금 당장 intent 하지 않을 때
        //(기본 Intent 는 만들자마자 바로 실행, 대기X)
        PendingIntent pi=PendingIntent.getActivity
                (this,17,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);//Pending(보류중)Intent

        //알림 확인시 자동 알람 삭제/setContentIntent() 했을 때만 가능
        builder.setAutoCancel(true);

        //Notification 객체 생성
        Notification notification=builder.build();//빌더에게 빌드 요청해 객체 생성
        //매니저에게 알림을 보이도록 공지
        manager.notify(17,notification);//식별번호, Notification 객체
    }
}
