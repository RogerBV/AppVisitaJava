package com.roger.appvisitajava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.dao.VisitClientDAO;
import com.roger.db.VisitClientTable;

public class VisitsListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    ListView lstVisits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits_list);
        lstVisits = (ListView)findViewById(R.id.lstVisits);
        lstVisits.setOnItemClickListener(this);
        lstVisits.setOnItemLongClickListener(this);
        ListarVisitas();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = ((SimpleCursorAdapter)lstVisits.getAdapter()).getCursor();
        c.moveToPosition(position);

        String cDNI = c.getString(0);

        Intent i = new Intent(getApplicationContext(), MapsActivity.class );
        i.putExtra("iTipo",1);
        i.putExtra("cDNI" ,cDNI  );
        startActivity(i);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor c = ((SimpleCursorAdapter)lstVisits.getAdapter()).getCursor();
        c.moveToPosition(position);
        Toast.makeText(getApplicationContext(),c.getString(0),Toast.LENGTH_LONG).show();
        return false;
    }
    private void ListarVisitas(){
        String[] from = new String[]{VisitClientTable.cClient, "Coordenadas"   };
        int[] to = new int[]{android.R.id.text1,android.R.id.text2};
        //int[] to = new int[]{R.id.texto1,R.id.texto1};
        VisitClientDAO clienteVisitaDAO = new VisitClientDAO(getApplicationContext());
        Cursor c =  clienteVisitaDAO.ListarVisitas();
        SimpleCursorAdapter mAdapter =
                new SimpleCursorAdapter(getApplicationContext(), android.R.layout.two_line_list_item, c, from, to){
                    /*simple_list_item_multiple_choice*/
                    /*simple_list_item_checked */
                    /*simple_expandable_list_item_1 */
                    /*android.R.layout.simple_expandable_list_item_2*/
                    /*select_dialog_multichoice*/
                    /*two_line_list_item*/
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view =super.getView(position, convertView, parent);
                        TextView textView=(TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK );

                        TextView textView1=(TextView) view.findViewById(android.R.id.text2);
                        textView1.setTextColor(Color.BLACK );

                        return view;
                        //return super.getView(position, convertView, parent);
                    }
                };
        mAdapter.notifyDataSetChanged();
        lstVisits.setAdapter(mAdapter);
        lstVisits.setItemsCanFocus(false);
        //lstVisitas.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //lstVisitas.setOnItemClickListener(new CheckBoxClick());
    }
    public class CheckBoxClick implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            CheckedTextView ctv = (CheckedTextView)arg1;
            if(ctv.isChecked()){
                Toast.makeText(getApplicationContext(), "now it is unchecked", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "now it is checked", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
