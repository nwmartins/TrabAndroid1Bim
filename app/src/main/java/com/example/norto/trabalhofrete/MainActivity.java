package com.example.norto.trabalhofrete;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.norto.trabalhofrete.Model.Cidade;
import com.example.norto.trabalhofrete.Model.Estado;
import com.example.norto.trabalhofrete.Model.ValorFrete;
import com.example.norto.trabalhofrete.util.Dados;
import com.example.norto.trabalhofrete.util.Mensagem;
import com.example.norto.trabalhofrete.util.TipoMensagem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btOrigem, btDestino, btCalcular;
    private TextView tvOrigem, tvDestino, tvResultado;
    private EditText etPeso;

    // CONSTANTES
    private final int ORIGEM = 1, DESTINO = 2;

    private Estado ufOrigem, ufDestino = new Estado();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        loadComponets();
        if (Dados.estadoList.size() == 0)
            loadData();
    }

    private void loadComponets() {
        btOrigem = findViewById(R.id.btOrigem);
        btDestino = findViewById(R.id.btDestino);
        btCalcular = findViewById(R.id.btCalcular);
        tvDestino = findViewById(R.id.tvDestino);
        tvOrigem = findViewById(R.id.tvOrigem);
        etPeso = findViewById(R.id.etPeso);
        tvResultado = findViewById(R.id.tvResultado);

        // ========= EVENTOS ==========
        loadEvents();
    }

    private void loadEvents() {
        btOrigem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnderecoActivity.class);
                intent.putExtra("TIPO", 1);
                startActivityForResult(intent, ORIGEM);
            }
        });
        btDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnderecoActivity.class);
                intent.putExtra("TIPO", 2);
                startActivityForResult(intent, DESTINO);
            }
        });
        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPeso.getText().toString().trim().length() > 0 && tvDestino.getText().toString().trim().length() > 0 && tvOrigem.getText().toString().trim().length() > 0) {
                    for (ValorFrete vf : Dados.valorFreteList) {
                        if ((ufOrigem.getId() == vf.getUfOrigem().getId()) && (ufDestino.getId() == vf.getUfDestino().getId())) {
                            tvResultado.setText("Valor Total Frete: R$" + ((Double.parseDouble(etPeso.getText().toString())) * (vf.getValorFrete())));
                            break; //Todo: Ver comando para cair fora... pq sempre executa a msg; = Variavel Controle
                        }
                    }
                    Mensagem.ExibirMensagem(MainActivity.this, "Valores do Frete para está combinação de Estado(s) de Origem e Destino, não foi encontrada", TipoMensagem.ALERTA);
                } else {
                    Mensagem.ExibirMensagem(MainActivity.this, "Por Favor Preencha Todos Os Campos", TipoMensagem.ALERTA);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //TODO: Verificar se não tem como pegar a partir do ESTADO as outras propriedades
        //TODO: Fazer ToString Personalizado ??

        switch (resultCode) {
            case RESULT_CANCELED:
                break;
            case RESULT_OK:
                Estado estado = (Estado) data.getExtras().get("UFSEL");
                Cidade cidade = (Cidade) data.getExtras().get("CIDADESEL");
                int cep = (int) data.getExtras().get("CEPSEL");

                switch (requestCode) {
                    case ORIGEM:
                        tvOrigem.setText(estado.getSigla() + " - " + cidade.getNome() + " - " + cep);
                        ufOrigem = estado;
                        break;
                    case DESTINO:
                        tvDestino.setText(estado.getSigla() + " - " + cidade.getNome() + " - " + cep);
                        ufDestino = estado;
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = null;

        int id = item.getItemId();

        if (id == R.id.nav_calcFrete) {

        } else if (id == R.id.nav_cadEstado) {
            intent = new Intent(MainActivity.this, CadastrosActivity.class);
            intent.putExtra("TPCAD", 1);
        } else if (id == R.id.nav_cadCidade) {
            intent = new Intent(MainActivity.this, CadastrosActivity.class);
            intent.putExtra("TPCAD", 2);
        } else if (id == R.id.nav_cadCEP) {
            intent = new Intent(MainActivity.this, CadastrosActivity.class);
            intent.putExtra("TPCAD", 3);
        } else if (id == R.id.nav_cadVlFrete) {
            intent = new Intent(MainActivity.this, CadValorFreteActivity.class);
            intent.putExtra("TPCAD", 4);
        }
        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadData() {
        List<Cidade> cidadeList = new ArrayList<>();
        List<Integer> cepList = new ArrayList<>();
        cepList.add(85960000);
        cepList.add(85903490);
        cepList.add(85906250);
        cidadeList.add(new Cidade(1, "Marechal Cândido Rondon", cepList));
        cepList = new ArrayList<>();

        cepList.add(85915060);
        cepList.add(85903490);
        cepList.add(85903640);
        cidadeList.add(new Cidade(2, "Toledo", cepList));
        cepList = new ArrayList<>();

        cepList.add(85906707);
        cepList.add(85905270);
        cepList.add(85905340);
        cidadeList.add(new Cidade(3, "Curitiba", cepList));
        Dados.estadoList.add(new Estado(1, "PR", "Paraná", cidadeList));
        //ESTADO 1

        cidadeList = new ArrayList<>();
        cepList = new ArrayList<>();
        cepList.add(85906735);
        cepList.add(85907436);
        cepList.add(85901047);
        cidadeList.add(new Cidade(4, "Blumenau", cepList));

        cepList = new ArrayList<>();
        cepList.add(85905630);
        cepList.add(85911230);
        cepList.add(85908310);
        cidadeList.add(new Cidade(5, "Chapecó", cepList));

        cepList = new ArrayList<>();
        cepList.add(85914030);
        cepList.add(85904515);
        cepList.add(85902210);
        cidadeList.add(new Cidade(6, "Nagegantes", cepList));
        Dados.estadoList.add(new Estado(2, "SC", "Santa-Catarina", cidadeList));

        cidadeList = new ArrayList<>();
        cepList = new ArrayList<>();
        cepList.add(85915215);
        cepList.add(85903240);
        cepList.add(85900020);
        cidadeList.add(new Cidade(7, "Rio Grande", cepList));

        cepList = new ArrayList<>();
        cepList.add(85905010);
        cepList.add(85901210);
        cepList.add(85900270);
        cidadeList.add(new Cidade(8, "Santa Maria", cepList));

        cepList = new ArrayList<>();
        cepList.add(85905050);
        cepList.add(85903650);
        cepList.add(85911106);
        cidadeList.add(new Cidade(9, "Passo Fundo", cepList));
        Dados.estadoList.add(new Estado(3, "RS", "Rio Grande Do Sul", cidadeList));

        Dados.valorFreteList.add(new ValorFrete(1, getEstadoById(1), getEstadoById(1), 2));
        Dados.valorFreteList.add(new ValorFrete(2, getEstadoById(1), getEstadoById(2), 4));
        Dados.valorFreteList.add(new ValorFrete(3, getEstadoById(1), getEstadoById(3), 16));
        Dados.valorFreteList.add(new ValorFrete(4, getEstadoById(2), getEstadoById(1), 32));
        Dados.valorFreteList.add(new ValorFrete(5, getEstadoById(2), getEstadoById(2), 64));
        Dados.valorFreteList.add(new ValorFrete(6, getEstadoById(2), getEstadoById(3), 128));
        Dados.valorFreteList.add(new ValorFrete(7, getEstadoById(3), getEstadoById(1), 256));
        Dados.valorFreteList.add(new ValorFrete(8, getEstadoById(3), getEstadoById(2), 512));
        Dados.valorFreteList.add(new ValorFrete(9, getEstadoById(3), getEstadoById(3), 1024));
    }

    private Estado getEstadoById(int id) {
        for (Estado e : Dados.estadoList) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
