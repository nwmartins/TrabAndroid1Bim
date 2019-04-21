package com.example.norto.trabalhofrete;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.norto.trabalhofrete.Model.Estado;
import com.example.norto.trabalhofrete.util.Dados;

public class CadValorFreteActivity extends AppCompatActivity {

    private Spinner spUfOrigem, spUfDestino;
    private EditText etVlFrete;
    private Button btSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_valor_frete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadComponents();
    }

    private void loadComponents() {
        spUfOrigem = findViewById(R.id.spUfOrigem);
        spUfDestino = findViewById(R.id.spUfDestino);
        btSalvar = findViewById(R.id.btSalvar);
        etVlFrete = findViewById(R.id.etVlFrete);

        fillSpinner();

    }

    private void fillSpinner() {

        ArrayAdapter<Estado> estadoArrayAdapter = new ArrayAdapter<>(
                CadValorFreteActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                Dados.estadoList);
        spUfOrigem.setAdapter(estadoArrayAdapter);
        spUfDestino.setAdapter(estadoArrayAdapter);
    }

}
