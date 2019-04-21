package com.example.norto.trabalhofrete;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.norto.trabalhofrete.Model.Cidade;
import com.example.norto.trabalhofrete.Model.Estado;
import com.example.norto.trabalhofrete.util.Dados;
import com.example.norto.trabalhofrete.util.Mensagem;
import com.example.norto.trabalhofrete.util.TipoMensagem;

import java.util.ArrayList;
import java.util.List;

public class CadastrosActivity extends AppCompatActivity {

    private Spinner spUfCad, spCidadeCad;
    private TextView tvUfCad, tvCidadeCad, tvCepCad, tvValorFrete;
    private EditText etUfSigla, etUfDescricao, etCidade, etCepCad, etVlFrete;
    private Button btSalvar;

    private int tpTela;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastros);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadComponents();
        checkTpForm();

    }

    private void loadComponents() {
        spUfCad = findViewById(R.id.spUfCad);
        spCidadeCad = findViewById(R.id.spCidadeCad);
        etUfSigla = findViewById(R.id.etCadUfSigla);
        etUfDescricao = findViewById(R.id.etCadUfDescricao);
        etCidade = findViewById(R.id.etCadCidadeDescricao);
        etCepCad = findViewById(R.id.etCadCep);
        tvUfCad = findViewById(R.id.tvUfCad);
        tvCidadeCad = findViewById(R.id.tvCidadeCad);
        tvCepCad = findViewById(R.id.tvCepCad);
        btSalvar = findViewById(R.id.btSalvar);
        etVlFrete = findViewById(R.id.etVlFrete);
        tvValorFrete = findViewById(R.id.tvVlFrete);

        fillSpinner();

    }

    private void loadEvents() {
        //Todo: Verificar quando ESTADO Não tem cidade e cep cadastrados...

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Estado estado = new Estado();
                Cidade cidade = new Cidade();
                List<Cidade> cidadeList;

                switch (tpTela) {
                    case 1:
                        estado.setId(getLastIdEstado(Dados.estadoList));
                        estado.setSigla(etUfSigla.getText().toString().trim());
                        estado.setDescricao(etUfDescricao.getText().toString().trim());
                        estado.setCidadeList(null);
                        Dados.estadoList.add(estado);

                        msg = "Estado Cadastrado com Sucesso";
                        break;

                    case 2:
                        estado = (Estado) spUfCad.getSelectedItem();
                        if (estado.getCidadeList() == null) {
                            cidadeList = new ArrayList<>();
                            cidade.setId(1);
                        } else {
                            cidadeList = estado.getCidadeList();
                            cidade.setId(getLastIdCidade(estado.getCidadeList()));
                        }
                        cidade.setNome(etCidade.getText().toString().trim());
                        cidade.setCep(null);
                        cidadeList.add(cidade);
                        estado.setCidadeList(cidadeList);

                        msg = "Cidade Cadastrada Com Sucesso";
                        break;

                    case 3:
                        estado = (Estado) spUfCad.getSelectedItem();
                        cidadeList = estado.getCidadeList();
                        cidade = (Cidade) spCidadeCad.getSelectedItem();
                        estado.getCidadeList().remove(cidade);
                        List<Integer> ceps;
                        int cep;
                        cep = Integer.parseInt(etCepCad.getText().toString().trim());
                        if (cidade.getCep() == null) {
                            ceps = new ArrayList<>();
                            ceps.add(cep);
                        } else {
                            ceps = cidade.getCep();
                            ceps.add(cep);
                        }
                        cidade.setCep(ceps);
                        cidadeList.add(cidade);
                        estado.setCidadeList(cidadeList);

                        msg = "Cep Cadastrado Com Sucesso";
                        break;
                    case 4:
                    //Todo: Parei aqui... Terminar cadastro de Valor Frete


                }
                Mensagem.ExibirMensagem(CadastrosActivity.this, msg, TipoMensagem.SUCESSO);
            }
        });

        spUfCad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tpTela == 3) {
                    refreshSpCidade();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillSpinner() {

        ArrayAdapter<Estado> estadoArrayAdapter = new ArrayAdapter<>(
                CadastrosActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                Dados.estadoList);
        spUfCad.setAdapter(estadoArrayAdapter);

        refreshSpCidade();

        loadEvents();
    }

    private void refreshSpCidade() {
        Estado estado = (Estado) spUfCad.getSelectedItem(); //Recebe o Objeto q já está selecionado;

        for (Estado e : Dados.estadoList) {
            if (estado.getId() == e.getId()) {
                if (Dados.cidadeList.size() > 0)
                    Dados.cidadeList.clear();
                Dados.cidadeList.addAll(e.getCidadeList()); //Só vai add as Cidades q são daquele Estado
            }
        }

        ArrayAdapter<Cidade> cidadeArrayAdapter = new ArrayAdapter<>(
                CadastrosActivity.this,
                R.layout.support_simple_spinner_dropdown_item,
                Dados.cidadeList); //Lista vem populada
        spCidadeCad.setAdapter(cidadeArrayAdapter);
    }

    private int getLastIdEstado(List<Estado> estados) {
        if (estados != null)
            return (estados.size()) + 1;
        else
            return 0;
    }

    private int getLastIdCidade(List<Cidade> cidades) {
        if (cidades != null)
            return (cidades.size()) + 1;
        else
            return 0;
    }

    private void checkTpForm() {
        switch ((tpTela = (int) getIntent().getExtras().get("TPCAD"))) {
            //Todo: Deixar somente um EditText de descrição pois é padrão...
            case 1: // Cadastro de ESTADO;
                spUfCad.setVisibility(View.GONE);
                spCidadeCad.setVisibility(View.GONE);
                etUfSigla.setVisibility(View.VISIBLE);
                etUfDescricao.setVisibility(View.VISIBLE);
                etCidade.setVisibility(View.GONE);
                etCepCad.setVisibility(View.GONE);
                tvUfCad.setVisibility(View.VISIBLE);
                tvCidadeCad.setVisibility(View.GONE);
                tvCepCad.setVisibility(View.GONE);
                tvValorFrete.setVisibility(View.GONE);
                etVlFrete.setVisibility(View.GONE);
                break;
            case 2: // Cadastro de Cidade
                spUfCad.setVisibility(View.VISIBLE);
                spCidadeCad.setVisibility(View.GONE);
                etUfSigla.setVisibility(View.GONE);
                etUfDescricao.setVisibility(View.GONE);
                etCidade.setVisibility(View.VISIBLE);
                etCepCad.setVisibility(View.GONE);
                tvUfCad.setVisibility(View.VISIBLE);
                tvCidadeCad.setVisibility(View.VISIBLE);
                tvCepCad.setVisibility(View.GONE);
                tvValorFrete.setVisibility(View.GONE);
                etVlFrete.setVisibility(View.GONE);
                break;
            case 3: // Cadastro de CEP
                spUfCad.setVisibility(View.VISIBLE);
                spCidadeCad.setVisibility(View.VISIBLE);
                etUfSigla.setVisibility(View.GONE);
                etUfDescricao.setVisibility(View.GONE);
                etCidade.setVisibility(View.GONE);
                etCepCad.setVisibility(View.VISIBLE);
                tvUfCad.setVisibility(View.VISIBLE);
                tvCidadeCad.setVisibility(View.VISIBLE);
                tvCepCad.setVisibility(View.VISIBLE);
                tvValorFrete.setVisibility(View.GONE);
                etVlFrete.setVisibility(View.GONE);
                break;
            case 4:
                spUfCad.setVisibility(View.VISIBLE);
                spCidadeCad.setVisibility(View.VISIBLE);
                etUfSigla.setVisibility(View.GONE);
                etUfDescricao.setVisibility(View.GONE);
                etCidade.setVisibility(View.GONE);
                etCepCad.setVisibility(View.GONE);
                tvUfCad.setVisibility(View.VISIBLE);
                tvCidadeCad.setVisibility(View.VISIBLE);
                tvCepCad.setVisibility(View.GONE);
                tvValorFrete.setVisibility(View.VISIBLE);
                etVlFrete.setVisibility(View.VISIBLE);

        }

    }

}
