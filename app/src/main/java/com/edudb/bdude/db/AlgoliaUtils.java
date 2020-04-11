package com.edudb.bdude.db;

import android.util.Log;
import com.algolia.search.saas.AlgoliaException;
import com.edudb.bdude.db.modules.Post;
import com.edudb.bdude.db.modules.algolia.AlgoliaModel;
import com.edudb.bdude.general.utils.Utils;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.util.List;

public class AlgoliaUtils {

    public static AlgoliaModel getAlgoliaResult(JSONObject jsonObject, AlgoliaException e, List<Post> out) {

        if (e != null) {
            e.printStackTrace();
        }

        if (jsonObject != null) { //success
            AlgoliaModel result = Utils.tryParseJson(jsonObject.toString(), AlgoliaModel.class);
            if (result != null) {
                Log.d(AlgoliaUtils.class.getSimpleName(), jsonObject.toString());
                String json = Utils.parseToJson(result.getHits());
                List<Post> tmp = Utils.tryParseJsonType(json, new TypeToken<List<Post>>() {
                }.getType());

                if (tmp != null && out != null)
                    out.addAll(tmp);
                return result;
            }
        }
        return null;
    }
}
