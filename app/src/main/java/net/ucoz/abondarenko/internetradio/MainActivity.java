package net.ucoz.abondarenko.internetradio;



import android.app.LoaderManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import net.ucoz.abondarenko.internetradio.dataBase.DB;
import net.ucoz.abondarenko.internetradio.dataBase.DBHelper;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DialogSaveNewStation.OnDialogSaveEventListener {

    private static final int CM_DELETE_ID = 1;
    private static final int LOADER_ID = 1;
    private ListView listViewStation;
    private DB dataBase;
    private SimpleCursorAdapter simpleCursorAdapter;
    private TextView textViewInfoStation;
    private Button buttonPlay;
    private Button buttonStop;
    private Button buttonAddNewStation;
    private String nameStation = "";
    private String linkStation;
    private DialogSaveNewStation dialogSaveNewStation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new DB(this);
        dataBase.openDB();
        dialogSaveNewStation = new DialogSaveNewStation();

        textViewInfoStation = (TextView) findViewById(R.id.txtInfo);
        buttonPlay = (Button) findViewById(R.id.btnPlay);
        buttonStop = (Button) findViewById(R.id.btnPause);
        buttonAddNewStation = (Button)findViewById(R.id.btnAddStation);


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkStation != null) {
                    Intent intent = new Intent(MainActivity.this, PlayService.class);
                    intent.putExtra("linkStation", linkStation);
                    intent.putExtra("nameStation", nameStation);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);

                    } else {
                        startService(intent);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "You don't select station", Toast.LENGTH_LONG).show();
                }

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayService.class);
                stopService(intent);
            }
        });

        buttonAddNewStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSaveNewStation.show(getFragmentManager(), "DialogSaveNewStation");
            }
        });


        listViewStation = (ListView) findViewById(R.id.lViewStation);
        String[] from = {DBHelper.COLUMN_NAME};
        int[] to = {R.id.txtNameStation};
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.item_list, null, from, to, 0);
        listViewStation.setAdapter(simpleCursorAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        registerForContextMenu(listViewStation);
        listViewStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PlayService.class);
                stopService(intent);
                Cursor cursor = dataBase.getItemDataDB(id);
                cursor.moveToFirst();
                int nameId = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                int linkId = cursor.getColumnIndex(DBHelper.COLUMN_RADIO_LINK);
                nameStation = cursor.getString(nameId);
                textViewInfoStation.setText(nameStation);
                linkStation = cursor.getString(linkId);
                cursor.close();
                intent = new Intent(MainActivity.this, PlayService.class);
                intent.putExtra("linkStation", linkStation);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.exit));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.deleted_record);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == CM_DELETE_ID) {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            dataBase.delRecDB(adapterContextMenuInfo.id);
            getLoaderManager().getLoader(LOADER_ID).forceLoad();
            return true;
        }
        return  super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBase.closeDB();
        Intent intent = new Intent(MainActivity.this, PlayService.class);
        stopService(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, dataBase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void saveDataStationListener(String name, String link) {
        Log.d("MyTag", name + " " + link);
        dataBase.addRecDB("Name", "linkStation");
        getLoaderManager().getLoader(LOADER_ID).forceLoad();
        dialogSaveNewStation.dismiss();
    }

    static class MyCursorLoader extends CursorLoader {
        DB dataBase;

        public MyCursorLoader(Context context, DB dataBase) {
            super(context);
            this.dataBase = dataBase;
        }

        @Override
        protected Cursor onLoadInBackground() {
            Cursor cursor = dataBase.getAllDataDB();
            return cursor;
        }
    }

}