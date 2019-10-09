package com.github.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import android.app.NotificationChannel
import android.os.Build


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btnSimple: Button? = null
    private var btnProgress: Button? = null
    private var btnBigText: Button? = null
    private var btnInBox: Button? = null
    private var btnBigPicture: Button? = null
    private var btnHangUp: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSimple = findViewById<View>(R.id.simple_notify) as Button
        btnSimple!!.setOnClickListener(this)

        btnProgress = findViewById<View>(R.id.progress) as Button
        btnProgress!!.setOnClickListener(this)

        btnBigText = findViewById<View>(R.id.big_text) as Button
        btnBigText!!.setOnClickListener(this)

        btnInBox = findViewById<View>(R.id.inbox) as Button
        btnInBox!!.setOnClickListener(this)

        btnBigPicture = findViewById<View>(R.id.big_picture) as Button
        btnBigPicture!!.setOnClickListener(this)

        btnHangUp = findViewById<View>(R.id.hangup) as Button
        btnHangUp!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (v === btnSimple) {
            simpleNotify()
        } else if (v === btnProgress) {
            progressNotify()
        } else if (v === btnBigText) {
            bigTextNotify()
        } else if (v === btnInBox) {
            inboxNotify()
        } else if (v === btnBigPicture) {
            bigPictureNotify()
        } else if (v === btnHangUp) {
            hangUpNotify()
        }
    }

    private fun simpleNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID) // 8.0之后必须有ID
        //Ticker是状态栏显示的提示
        builder.setTicker("简单Notification")
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("标题")
        //第二行内容 通常是通知正文
        builder.setContentText("通知内容")
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("这里显示的是通知第三行内容！")
        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
        //builder.setContentInfo("2");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(2)
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true)
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
        val intent = Intent(this, NotificationActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        //点击跳转的intent
        builder.setContentIntent(pIntent)
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        val notification = builder.build()
        manager.notify(TYPE_NORMAL, notification)
    }

    private fun progressNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID) // 8.0之后必须有ID
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false)
        //禁止滑动删除
        builder.setOngoing(true)
        //取消右上角的时间显示
        builder.setShowWhen(false)
        builder.setContentTitle("下载中...7%")
        builder.setProgress(100, 7, false)
        //builder.setContentInfo(progress+"%");
        builder.setOngoing(true)
        builder.setShowWhen(false)
        val intent = Intent(this, NotificationActivity::class.java)
        intent.putExtra("command", 1)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        //点击跳转的intent
        builder.setContentIntent(pIntent)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        val notification = builder.build()
        manager.notify(TYPE_PROGRESS, notification)
    }

    private fun bigTextNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID) // 8.0之后必须有ID
        builder.setContentTitle("BigTextStyle")
        builder.setContentText("BigTextStyle演示示例")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
        val style = NotificationCompat.BigTextStyle()
        style.bigText("这里是点击通知后要显示的正文，可以换行可以显示很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长")
        style.setBigContentTitle("点击后的标题")
        //SummaryText没什么用 可以不设置
        style.setSummaryText("末尾只一行的文字内容")
        builder.setStyle(style)
        builder.setAutoCancel(true)
        val intent = Intent(this, NotificationActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        builder.setContentIntent(pIntent)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        val notification = builder.build()
        manager.notify(TYPE_BIG_TEXT, notification)
    }

    private fun inboxNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID) // 8.0之后必须有ID
        builder.setContentTitle("InboxStyle")
        builder.setContentText("InboxStyle演示示例")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
        val style = NotificationCompat.InboxStyle()
        style.setBigContentTitle("BigContentTitle")
                .addLine("第一行，第一行，第一行，第一行，第一行，第一行，第一行")
                .addLine("第二行")
                .addLine("第三行")
                .addLine("第四行")
                .addLine("第五行")
                .setSummaryText("SummaryText")
        builder.setStyle(style)
        builder.setAutoCancel(true)
        val intent = Intent(this, NotificationActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        builder.setContentIntent(pIntent)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        val notification = builder.build()
        manager.notify(TYPE_INBOX, notification)
    }

    private fun bigPictureNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID) // 8.0之后必须有ID
        builder.setContentTitle("BigPictureStyle")
        builder.setContentText("BigPicture演示示例")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
        val style = NotificationCompat.BigPictureStyle()
        style.setBigContentTitle("BigContentTitle")
        style.setSummaryText("SummaryText")
        style.bigPicture(BitmapFactory.decodeResource(resources, R.mipmap.img_power_popup_notice))
        builder.setStyle(style)
        builder.setAutoCancel(true)
        val intent = Intent(this, NotificationActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        //设置点击大图后跳转
        builder.setContentIntent(pIntent)
        val notification = builder.build()
        manager.notify(TYPE_BIG_PICTURE, notification)
    }


    private fun hangUpNotify() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(this, PUSH_CHANNEL_ID) // 8.0之后必须有ID
        builder.setContentTitle("横幅通知")
        builder.setContentText("请在设置通知管理中开启消息横幅提醒权限")
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
        val intent = Intent(this, NotificationActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 1, intent, 0)
        builder.setContentIntent(pIntent)
        //这句是重点
        builder.setFullScreenIntent(pIntent, true)
        builder.setAutoCancel(true)
        val notification = builder.build()
        manager.notify(TYPE_HANGUP, notification)

        Handler().postDelayed({
            manager.cancel(TYPE_HANGUP)
            val builder = NotificationCompat.Builder(this@MainActivity)
            builder.setContentTitle("横幅通知")
            builder.setContentText("请在设置通知管理中开启消息横幅提醒权限")
            builder.setSmallIcon(R.mipmap.ic_launcher)
            builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.contact))
            val intent = Intent(this@MainActivity, NotificationActivity::class.java)
            val pIntent = PendingIntent.getActivity(this@MainActivity, 1, intent, 0)
            builder.setContentIntent(pIntent)
            builder.setAutoCancel(true)
            val notification = builder.build()
            manager.notify(TYPE_HANGUP, notification)
        }, 2000)
    }

    companion object {
        val PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID"  // 不能太长且在当前应用中是唯一的，太长会被截取
        val PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME" // 不能太长推荐40个字符，太长同样会被截取

        val TYPE_NORMAL = 1
        val TYPE_PROGRESS = 2
        val TYPE_BIG_TEXT = 3
        val TYPE_INBOX = 4
        val TYPE_BIG_PICTURE = 5
        val TYPE_HANGUP = 6
    }
}
