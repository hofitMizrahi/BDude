package com.edudb.bdude.db;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.edudb.bdude.BuildConfig;
import com.google.firebase.firestore.GeoPoint;

public class AlgoliaSearch {

    private static AlgoliaSearch mInstance;
    private Client mClient;
    private Index mIndex;
    private final String ALGOLIA_KEY = "5b0755921dd19ee2aefa42d360ee0aab";
    private final String APPLICATION_ID_ALGOLIA = "6URA2STDUH";

    public static AlgoliaSearch getInstance() {
        if (mInstance == null) {
            mInstance = new AlgoliaSearch();
        }
        return mInstance;
    }

    public Index getIndex() {
        initClient();
        return mIndex;
    }

    private void initClient() {
        mClient = new Client(APPLICATION_ID_ALGOLIA, ALGOLIA_KEY);
        mIndex = mClient.getIndex("posts");
    }
}
