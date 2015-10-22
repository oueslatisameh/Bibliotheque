package com.sarah_wissocq_adrien_agez.bibliotheque.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sarah_wissocq_adrien_agez.bibliotheque.R;
import com.sarah_wissocq_adrien_agez.bibliotheque.book.Book;
import com.sarah_wissocq_adrien_agez.bibliotheque.book.BookAdapter;
import com.sarah_wissocq_adrien_agez.bibliotheque.book.BookLibrary;


public class ViewBookList extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book_list);

        /** Créer une liste de livres */
        BookAdapter adapter = new BookAdapter(this, BookLibrary.LIBRARY);
        final ListView listView = (ListView) findViewById(R.id.lvBook);
        registerForContextMenu(listView);
        listView.setAdapter(adapter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_book_list, container, false);
    }




    @Override
    /**
     * Créer un menu permettant de modifier ou de supprimer
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lvBook) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    /**
     * Modifier / Supprimer
     */
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ListView listView = (ListView) findViewById(R.id.lvBook);
        switch(item.getItemId()) {
            case R.id.edit:
                // edit stuff here
                return true;
            case R.id.delete:
                /** Récupère le livre est le supprime de la librairie */
                BookLibrary.LIBRARY.removeBook((Book) listView.getAdapter().getItem(info.position));

                /** Affiche une boîte de dialogue pour confirmer que le livre a été supprimé */
                AlertDialog.Builder alert=new AlertDialog.Builder(this);
                alert.setTitle(R.string.suppress);
                alert.setMessage(R.string.BookSuppressed);
                alert.setPositiveButton(R.string.ok, new OkListener());
                alert.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    /**
     * Redémarre l'activité afin de ne plus voir le livre supprimé dans la liste.
     */
    private final class OkListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ViewBookList.this.finish();
            Intent intent = new Intent(ViewBookList.this, ViewBookList.class);
            startActivity(intent);
        }
    }
}


