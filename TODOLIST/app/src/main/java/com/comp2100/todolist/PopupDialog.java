package com.comp2100.todolist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class PopupDialog extends Dialog {
    Context mContext;
    Date taskID;
    TaskDB selecttask;
    public PopupDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }
    public PopupDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }
    public PopupDialog(Context context , Date id){
        super(context);
        taskID = id;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_dialog, null);
//        List<TaskDB> task = DatabaseClient
//                .getInstance(getContext())
//                .getAppDatabase()
//                .taskDao()
//                .gettask(taskID);

//        selecttask = task.get(0);

        setcontent();

        this.setContentView(layout);
    }
    public void setcontent(){
        TextView TitleText = findViewById(R.id.title);
        TextView DespText = findViewById(R.id.description);
        RadioGroup grp1 = findViewById(R.id.rgcolour);
        RadioGroup grp2 = findViewById(R.id.belowcolour);


        TitleText.setHint(selecttask.getTitle());
        DespText.setHint(selecttask.getDescription());

    }

}
