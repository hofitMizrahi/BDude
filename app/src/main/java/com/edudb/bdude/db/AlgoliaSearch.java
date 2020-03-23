package com.edudb.bdude.db;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.edudb.bdude.BuildConfig;

public class AlgoliaSearch {

    private static AlgoliaSearch mInstance;
    private Client mClient;
    private Index mIndex;
    private final String ALGOLIA_KEY = "5b0755921dd19ee2aefa42d360ee0aab";

    public static AlgoliaSearch getInstance() {
        if (mInstance == null) {
            mInstance = new AlgoliaSearch();
        }
        return mInstance;
    }

    public void initClient() {
        mClient = new Client(BuildConfig.APPLICATION_ID, ALGOLIA_KEY);
        mIndex = mClient.getIndex("posts");
    }
}
