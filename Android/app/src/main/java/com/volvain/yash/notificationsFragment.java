package com.volvain.yash;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;

import com.volvain.yash.DAO.Database;

import static com.volvain.yash.DAO.Database.TableHelp;

public class notificationsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View  v=inflater.inflate(R.layout.fragment_notifications,null);
      LinearLayout notificationContainer=(LinearLayout)v.findViewById(R.id.notificationContainer);



        Database db= new Database(this.getContext());
        Cursor rs=db.getCursor();
        while(rs.moveToNext())
        {
                    Long id = rs.getLong(0);
                    String name = rs.getString(1);
                    String message=rs.getString(4);
                    notificationContainer.addView(addTab(id, name,message));

        }


        //TODO Fetch id and name from help table
      return v;
    }
  /*  private Button addNotification(Long id,String name,String message){
        final Button  notificationButton=new Button(this.getContext());
        notificationButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        notificationButton.setText(name+" needs help\n\nMessage "+message+"\n\ncontact : "+id);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Button b=(Button)v;
                String text1=b.getText().toString();
                Long id=Long.parseLong(new StringBuffer(new StringBuffer(text1).reverse().substring(0,10)).reverse().toString());
                Log.i("gauravrmsc","a");
                WorkManager.getInstance().cancelAllWork();
              /* Intent intent=new Intent(notificationsFragment.this.getContext(),MapsActivity.class);
               intent.putExtra("id",id);
                Log.i("gauravrmsc","a1"+id);
               startActivity(intent);*/
              /*  String[] message = {""+id};
                Home home=((Home)getActivity());
                home.c=MapsActivity.class;
                home.args=message;
                home.checkPermissions(MapsActivity.class,message);
               //TODO Get id from here for search
            }
        });
       return notificationButton;
    }
*/
    LinearLayout addTab(final Long id, String name, String message){
        LinearLayout l1=new LinearLayout(this.getContext());
        LinearLayout l2=new LinearLayout(this.getContext());
        TextView v=new TextView(this.getContext());
        ImageButton call=new ImageButton(this.getContext());
        ImageButton locate=new ImageButton(this.getContext());
        l1.setOrientation(LinearLayout.VERTICAL);
        l2.setOrientation(LinearLayout.HORIZONTAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            v.setGravity(Gravity.CENTER);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable callImg=this.getContext().getDrawable(R.drawable.call);
            Drawable locImg=this.getContext().getDrawable(R.drawable.location);
            Drawable border=this.getContext().getDrawable(R.drawable.bg_round);
            call.setBackground(callImg);
            locate.setBackground(locImg);
            l1.setBackground(border);
        }
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams layoutParamsl1= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsl2= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutParamsl3= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsl1.setMargins(20,15,20,15);
        l1.setPadding(0,20,0,0);
        layoutParamsl2.setMargins(0,0,30,0);
        layoutParamsl3.setMargins(30,15,0,15);
        layoutParamsl2.gravity=Gravity.RIGHT;
        l1.setLayoutParams(layoutParamsl1);
        l2.setLayoutParams(layoutParamsl2);
        call.setLayoutParams(layoutParamsl3);
        locate.setLayoutParams(layoutParamsl3);
        v.setText(name+" needs help\nMessage "+message+"\ncontact : "+id);
        l1.addView(v);
        l1.addView(l2);
        l2.addView(call);
        l2.addView(locate);
        locate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().cancelAllWork();
                String[] message = {""+id};
                Home home=((Home)getActivity());
                home.c=MapsActivity.class;
                home.args=message;
                home.checkPermissions(MapsActivity.class,message);
                //TODO Get id from here for search
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+id.toString()));
                startActivity(i);
            }
        });

        return l1;
    }
}
