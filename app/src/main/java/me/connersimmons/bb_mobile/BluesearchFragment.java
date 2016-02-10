package me.connersimmons.bb_mobile;


import android.content.Context;
import android.content.OperationApplicationException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import me.connersimmons.bb_mobile.api.ContactAPI;
import me.connersimmons.bb_mobile.utils.ContactOperations;


/**
 * A simple {@link Fragment} subclass.
 */
public class BluesearchFragment extends Fragment {

    private static final String VCARD_URL = "http://www.cs.scranton.edu/~simmonsc3/vcard.vcf";
    String pageContents;

    public BluesearchFragment() {
        // Required empty public constructor
        pageContents = "";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_bluesearch,null);

        String url = "http://www.thebluebook.com/";
        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(R.string.title_bluesearch);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bluesearch, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_vcard_contact:
                /*
                Context context = getActivity().getApplicationContext();
                CharSequence text = "Add vcard contact!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */

                DownloadWebPageTask task = new DownloadWebPageTask();
                task.execute(new String[]{VCARD_URL});

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public class DownloadWebPageTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... urls) {
            URL vcardURL = null;
            try {
                vcardURL = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            BufferedReader in = null;
            try {
                in = new BufferedReader(
                        new InputStreamReader(vcardURL.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String inputLine = null;
            try {
                while ((inputLine = in.readLine()) != null)
                    pageContents += inputLine + "\n";
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(pageContents);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ContactOperations operations = new ContactOperations(getActivity().getApplicationContext());
            List<VCard> vcards = Ezvcard.parse(pageContents).all();
            for (VCard vcard : vcards) {
                /*
                String fullName = vcard.getFormattedName().getValue();
                String lastName = vcard.getStructuredName().getFamily();

                System.out.println("Full name: " + fullName);
                System.out.println("Last name: " + lastName);

                try {
                    operations.insertContact(vcard);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
                */
                System.out.println(vcard);
                ContactAPI.insertContact(getActivity().getContentResolver(), vcard);//vcard.getStructuredName().getGiven(), vcard.getTelephoneNumbers().get(0).getText());
            }
        }
    }

}
