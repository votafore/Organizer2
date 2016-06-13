package com.votafore.organizer;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.votafore.organizer.model.Task;
import com.votafore.organizer.model.TaskManager;

import java.util.ArrayList;
import java.util.List;


public class ActivityTask extends AppCompatActivity{

    public interface IDescriptionCreator{
        void createDescription(ExtraListItem item);
    }


    TaskManager mManager;

    Task mTask;

    ExtraSettingsAdapter mAdapter;

    private final int DIALOG_ID_PICK_SIGNAL = 1;
    private final int DIALOG_ID_PICK_DAYS_OF_WEEK = 2;
    private final int DIALOG_ID_PICK_MONTHS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent i = getIntent();

        mManager = TaskManager.getInstance(this);
        mTask = mManager.getTask(i.getIntExtra(ActivityMain.TASK_ID, 0));


        //TextInputLayout     til_title        = (TextInputLayout)findViewById(R.id.textinputlayout_title);
        EditText            et_title         = (EditText)findViewById(R.id.task_title);
        EditText            et_description   = (EditText)findViewById(R.id.task_description);
        TimePicker          timePicker       = (TimePicker)findViewById(R.id.timePicker);
        RecyclerView        rv_extraSettings = (RecyclerView)findViewById(R.id.extra_settings);


        et_title.setText(mTask.getTitle());
        et_description.setText(mTask.getDescription());

        et_title.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTask.setTitle(s.toString());
            }
        });

        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTask.setDescription(s.toString());
            }
        });


        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(mTask.getHour());
        timePicker.setCurrentMinute(mTask.getMinute());
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mTask.setTime(hourOfDay, minute);
            }
        });

        rv_extraSettings.setHasFixedSize(true);
        rv_extraSettings.setItemAnimator(new DefaultItemAnimator());
        rv_extraSettings.setLayoutManager(new LinearLayoutManager(this));

        // описываем пункты доп. настроек и определяем их поведение
        ExtraListItem item1, item2, item3;

        item1 = new ExtraListItem("сигнал уведомления");
        item2 = new ExtraListItem("настройка дней недель");
        item3 = new ExtraListItem("настройка месяцев");

        item1.setCreator(new DescriptionSound());
        item2.setCreator(new DescriptionWeekDays());
        item3.setCreator(new DescriptionMonths());

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_PICK_SIGNAL);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDialog(DIALOG_ID_PICK_DAYS_OF_WEEK);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_PICK_MONTHS);
            }
        });


        List<ExtraListItem> settings = new ArrayList<>();

        settings.add(item1);
        settings.add(item2);
        settings.add(item3);

        mAdapter = new ExtraSettingsAdapter(settings);

        rv_extraSettings.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_task, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.task_menu_save:
                mManager.saveTask(mTask);

                break;
            case R.id.task_menu_delete:
                mManager.deleteTask(mTask);
        }

        finish();

        return true;
    }





    @Override
    protected Dialog onCreateDialog(final int id) {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        DialogInterface.OnClickListener dialogListener;

        switch (id){
            case DIALOG_ID_PICK_SIGNAL:




                dialogListener = new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                };

                break;
            case DIALOG_ID_PICK_DAYS_OF_WEEK:
                adb.setTitle("выберите дни недели");

                String[] days = new String[]{
                        getResources().getString(R.string.mnd)
                        ,getResources().getString(R.string.tud)
                        ,getResources().getString(R.string.wnd)
                        ,getResources().getString(R.string.thd)
                        ,getResources().getString(R.string.frd)
                        ,getResources().getString(R.string.std)
                        ,getResources().getString(R.string.snd)
                };

                boolean[] checkDays = new boolean[]{
                        mTask.getDayOfWeek(1) == 1
                        ,mTask.getDayOfWeek(2) == 1
                        ,mTask.getDayOfWeek(3) == 1
                        ,mTask.getDayOfWeek(4) == 1
                        ,mTask.getDayOfWeek(5) == 1
                        ,mTask.getDayOfWeek(6) == 1
                        ,mTask.getDayOfWeek(7) == 1
                };

                adb.setMultiChoiceItems(days, checkDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });

                dialogListener = new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SparseBooleanArray sbArray = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
                        for (int i = 0; i < sbArray.size(); i++) {
                            int key = sbArray.keyAt(i);
                            int dayOfWeek = key + 1;

                            mTask.setDayOfWeek(dayOfWeek, sbArray.get(key)?1:0);
                        }

                        mAdapter.getChildAt(id-1).createDescription();
                        mAdapter.notifyItemChanged(id-1);
                    }
                };
                break;
            case DIALOG_ID_PICK_MONTHS:

                adb.setTitle("выберите месяцы");

                String[] months = new String[]{
                        getResources().getString(R.string.jan)
                        ,getResources().getString(R.string.fb)
                        ,getResources().getString(R.string.mch)
                        ,getResources().getString(R.string.apr)
                        ,getResources().getString(R.string.may)
                        ,getResources().getString(R.string.jn)
                        ,getResources().getString(R.string.jl)
                        ,getResources().getString(R.string.ag)
                        ,getResources().getString(R.string.sp)
                        ,getResources().getString(R.string.oct)
                        ,getResources().getString(R.string.nov)
                        ,getResources().getString(R.string.dec)
                };

                boolean[] checkMonths = new boolean[]{
                        mTask.getMonths(1) == 1
                        ,mTask.getMonths(2) == 1
                        ,mTask.getMonths(3) == 1
                        ,mTask.getMonths(4) == 1
                        ,mTask.getMonths(5) == 1
                        ,mTask.getMonths(6) == 1
                        ,mTask.getMonths(7) == 1
                        ,mTask.getMonths(8) == 1
                        ,mTask.getMonths(9) == 1
                        ,mTask.getMonths(10) == 1
                        ,mTask.getMonths(11) == 1
                        ,mTask.getMonths(12) == 1
                };

                adb.setMultiChoiceItems(months, checkMonths, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });

                dialogListener = new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SparseBooleanArray sbArray = ((AlertDialog)dialog).getListView().getCheckedItemPositions();
                        for (int i = 0; i < sbArray.size(); i++) {
                            int key = sbArray.keyAt(i);
                            int mMonth = key + 1;
                            mTask.setMonth(mMonth, sbArray.get(key)?1:0);
                        }

                        mAdapter.getChildAt(id-1).createDescription();
                        mAdapter.notifyItemChanged(id-1);
                    }
                };
                break;
            default:

                dialogListener = new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                };
        }

        adb.setPositiveButton("OK", dialogListener);

        return adb.create();
    }







    public class ExtraSettingsAdapter extends RecyclerView.Adapter<ExtraSettingsAdapter.ViewHolder>{

        List<ExtraListItem> mListItems;

        public ExtraSettingsAdapter(List<ExtraListItem> listItems){
            mListItems = listItems;
        }

        public ExtraListItem getChildAt(int position){
            return mListItems.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_extra_settings, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.vh_title.setText(mListItems.get(position).getTitle());
            holder.vh_description.setText(mListItems.get(position).getDescription());
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView vh_title;
            TextView vh_description;

            public ViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(this);

                vh_title        = (TextView)itemView.findViewById(R.id.extraset_title);
                vh_description  = (TextView)itemView.findViewById(R.id.extraset_description);
            }

            @Override
            public void onClick(View v) {
                mListItems.get(getAdapterPosition()).getListener().onClick(v);
            }
        }
    }

    public class ExtraListItem{

        private String mTitle="";
        private String mDescription="";

        View.OnClickListener mListener;

        IDescriptionCreator mCreator;

        public ExtraListItem(String title){
            mTitle = title;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description){
            mDescription = description;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setOnClickListener(View.OnClickListener listener){
            mListener = listener;
        }

        public View.OnClickListener getListener(){
            return mListener;
        }

        public void setCreator(IDescriptionCreator creator){
            mCreator = creator;
            createDescription();
        }

        public void createDescription(){
            mCreator.createDescription(this);
        }
    }



    private class DescriptionSound implements IDescriptionCreator{

        @Override
        public void createDescription(ExtraListItem item) {
            // TODO: меняем описание звука в зависимости от выбранного файла (если вообще выбран)

            String description = "по умолчанию";




            item.setDescription(description);
        }
    }

    private class DescriptionWeekDays implements IDescriptionCreator{

        @Override
        public void createDescription(ExtraListItem item) {

            String description = "Каждый день";

            // если значения всех дней одинаково
            if(mTask.getDayOfWeek(1) == mTask.getDayOfWeek(2) &&
                    mTask.getDayOfWeek(1) == mTask.getDayOfWeek(3) &&
                    mTask.getDayOfWeek(1) == mTask.getDayOfWeek(4) &&
                    mTask.getDayOfWeek(1) == mTask.getDayOfWeek(5) &&
                    mTask.getDayOfWeek(1) == mTask.getDayOfWeek(6) &&
                    mTask.getDayOfWeek(1) == mTask.getDayOfWeek(7)){

                // установленные значения равны
                if(mTask.getDayOfWeek(1) == 0)
                    description = "Нет установленных дней (уведомлений не будет)";
            }else{

                ArrayList check = new ArrayList();
                ArrayList uncheck = new ArrayList();

                // попробуем определить каких дней больше (включенных или отключенных)
                for (int i=1; i <= 7; i++){

                    if(mTask.getDayOfWeek(i)==0){
                        uncheck.add(i-1);
                    }else{
                        check.add(i-1);
                    }
                }


                String[] days = new String[]{getResources().getString(R.string.mnd)
                        ,getResources().getString(R.string.tud)
                        ,getResources().getString(R.string.wnd)
                        ,getResources().getString(R.string.thd)
                        ,getResources().getString(R.string.frd)
                        ,getResources().getString(R.string.std)
                        ,getResources().getString(R.string.snd)};


                description = "Установленные дни: ";

                if(uncheck.size() < 3){
                    description = description.concat("все кроме ");
                    for(int n=0; n < uncheck.size(); n++){
                        description = description.concat(days[(int)uncheck.get(n)]);
                        description = description.concat(" ");
                    }
                }else{
                    for(int n=0; n < check.size(); n++){
                        description = description.concat(days[(int)check.get(n)]);
                        description = description.concat(" ");
                    }
                }
            }

            item.setDescription(description);
        }
    }

    private class DescriptionMonths implements IDescriptionCreator{

        @Override
        public void createDescription(ExtraListItem item) {

            String description = "Установлены месяцы: ";

            if(mTask.getMonths(1) == mTask.getMonths(2) &&
                    mTask.getMonths(1) == mTask.getMonths(3) &&
                    mTask.getMonths(1) == mTask.getMonths(4) &&
                    mTask.getMonths(1) == mTask.getMonths(5) &&
                    mTask.getMonths(1) == mTask.getMonths(6) &&
                    mTask.getMonths(1) == mTask.getMonths(7) &&
                    mTask.getMonths(1) == mTask.getMonths(8) &&
                    mTask.getMonths(1) == mTask.getMonths(9) &&
                    mTask.getMonths(1) == mTask.getMonths(10) &&
                    mTask.getMonths(1) == mTask.getMonths(11) &&
                    mTask.getMonths(1) == mTask.getMonths(12)){

                if(mTask.getMonths(1) == 1){
                    description = description.concat("все");
                }else{
                    description = "Ни один месяц не выбран. Уведомлений не будет";
                }
            }else{

                ArrayList check = new ArrayList();
                ArrayList uncheck = new ArrayList();

                // попробуем определить каких месяцев больше (включенных или отключенных)
                for (int i=1; i <= 12; i++){

                    if(mTask.getMonths(i)==0){
                        uncheck.add(i-1);
                    }else{
                        check.add(i-1);
                    }
                }


                String[] months = new String[]{
                        getResources().getString(R.string.jan)
                        ,getResources().getString(R.string.fb)
                        ,getResources().getString(R.string.mch)
                        ,getResources().getString(R.string.apr)
                        ,getResources().getString(R.string.may)
                        ,getResources().getString(R.string.jn)
                        ,getResources().getString(R.string.jl)
                        ,getResources().getString(R.string.ag)
                        ,getResources().getString(R.string.sp)
                        ,getResources().getString(R.string.oct)
                        ,getResources().getString(R.string.nov)
                        ,getResources().getString(R.string.dec)};

                if(uncheck.size() < 4){
                    description = description.concat("все кроме ");
                    for(int n=0; n < uncheck.size(); n++){
                        description = description.concat(months[(int)uncheck.get(n)]);
                        description = description.concat(" ");
                    }
                }else{
                    for(int n=0; n < check.size(); n++){
                        description = description.concat(months[(int)check.get(n)]);
                        description = description.concat(" ");
                    }
                }

            }

            item.setDescription(description);
        }
    }
}