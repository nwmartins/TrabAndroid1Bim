package com.example.norto.trabalhofrete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.norto.trabalhofrete.Model.Cidade;
import com.example.norto.trabalhofrete.Model.Estado;
import com.example.norto.trabalhofrete.util.Dados;

import java.io.Serializable;

public class EnderecoActivity extends AppCompatActivity {

    private int tpTela = 0;

    //Componentes
    private TextView tvEndereco;
    private Spinner spUf, spCidade, spCEP;
    private Button btSelecionar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadComponents();
        checkTpForm();

    }

    private void loadComponents() {
        tvEndereco = findViewById(R.id.tvEndereco);
        btSelecionar = findViewById(R.id.btSelecionar);
        spUf = findViewById(R.id.spUFs);
        spCidade = findViewById(R.id.spCidades);
        spCEP = findViewById(R.id.spCEPs);

        fillSpinner();
    }

    private void fillSpinner() {

        ArrayAdapter<Estado> estadoArrayAdapter = new ArrayAdapter<>(
                EnderecoActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                Dados.estadoList);
        spUf.setAdapter(estadoArrayAdapter);

        refreshSpCidade();

        refreshSpCep();

        loadEvents();
    }

    private void loadEvents() {
        spUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshSpCidade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshSpCep();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btSelecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estado estado = (Estado) spUf.getSelectedItem();
                Cidade cidade = (Cidade) spCidade.getSelectedItem();
                int cep = (int) spCEP.getSelectedItem();
                Intent output = new Intent();
                output.putExtra("UFSEL", estado);
                output.putExtra("CIDADESEL", cidade);
                output.putExtra("CEPSEL", cep);
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }

    private void refreshSpCep() {
        Cidade cidade = (Cidade) spCidade.getSelectedItem();

        for (Cidade c : Dados.cidadeList) {
            if (cidade.getId() == c.getId()) {
                if (Dados.CEPList.size() > 0)
                    Dados.CEPList.clear();
                Dados.CEPList.addAll(c.getCep()); //Só vai add as Cidades q são daquele Estado
            }
        }

        ArrayAdapter<Integer> cepArrayAdapter = new ArrayAdapter<>(
                EnderecoActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                Dados.CEPList); //Lista vem populada
        spCEP.setAdapter(cepArrayAdapter);
    }

    private void refreshSpCidade() {
        Estado estado = (Estado) spUf.getSelectedItem(); //Recebe o Objeto q já está selecionado;

        for (Estado e : Dados.estadoList) {
            if (estado.getId() == e.getId()) {
                    if (Dados.cidadeList.size() > 0)
                        Dados.cidadeList.clear();
                    Dados.cidadeList.addAll(e.getCidadeList()); //Só vai add as Cidades q são daquele Estado
                }
        }

        ArrayAdapter<Cidade> cidadeArrayAdapter = new ArrayAdapter<>(
                EnderecoActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                Dados.cidadeList); //Lista vem populada
        spCidade.setAdapter(cidadeArrayAdapter);
    }

    private void checkTpForm() {
        switch ((tpTela = (int) getIntent().getExtras().get("TIPO"))) {
            case 1:
                tvEndereco.setText(R.string.lbSelecioneOrigem);
                break;
            case 2:
                tvEndereco.setText(R.string.lbSelecioneDestino);
                break;
        }

    }

}
