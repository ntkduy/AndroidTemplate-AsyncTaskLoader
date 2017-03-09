package com.ntkduy1604.androidtemplate_asynctaskloader;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (TextView) findViewById(R.id.output);
    }

    public void runClickHandler(View view) {

        /**
         * The getSupportLoaderManager() method returns the LoaderManager object.
         * @initLoader (id, Bundle, LoaderCallBack): will create object once only
         * @forceLoad() will trigger the loadInBackGround method and start the whole chain of events
         * that goes and gets data and returns it to the activity
         */
        //getSupportLoaderManager().initLoader(0, null, this).forceLoad();

        /**
         * The getSupportLoaderManager() method returns the LoaderManager object.
         * @restartLoader (id, Bundle, LoaderCallBack): will recreate object each time it is called
         * @forceLoad() will trigger the loadInBackGround method and start the whole chain of events
         * that goes and gets data and returns it to the activity
         */
        getSupportLoaderManager().restartLoader(0, null, this).forceLoad();

    }

    public void clearClickHandler(View view) {
        output.setText("");
    }

    /**
     * Response to the call from @getSupportLoaderManager()
     * @param id
     * @param args
     * @return calls the MyAsyncTaskLoader to do its jobs in background
     */
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        output.append("Creating loader\n");
        return new MyAsyncTaskLoader(this);
    }

    /**
     * The data returned from the onCreateLoader will be analysed here
     * @param loader
     * @param data: the returned data from onCreateLoader method
     */
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        output.append("Loader finished, return: " + data + "\n");
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    /**
     * Only need to define the return value
     */
    private static class MyAsyncTaskLoader extends AsyncTaskLoader<String>{

        public MyAsyncTaskLoader(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "from the loader";
        }

        @Override
        public void deliverResult(String data) {
            data += ", delivered";
            super.deliverResult(data);
        }
    }
}
