package br.com.goldencode.softsports;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaInicialActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{

    AppCompatImageButton imageButton;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    Drawable drawable;
    CircleImageView circleImageView;
    TextView toolbarTextView;
    Softsports db = new Softsports(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        toolbarTextView =  findViewById(R.id.toolbarText);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Softsports");

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        circleImageView = findViewById(R.id.fotoPerfil);

        navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        toggle.syncState();
        toggle.setDrawerSlideAnimationEnabled(true);
        drawable = (Drawable) ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu, getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setDrawerIndicatorEnabled(false);

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        imageButton = findViewById(R.id.closeDrawer);

        /*   TESTE DO CRUD   */

        db.inserirEsportes(new Esporte("Futebol"));
        db.inserirEsportes(new Esporte("Basquete"));
        db.inserirEsportes(new Esporte("Tênis de mesa"));
        db.inserirEsportes(new Esporte("Tênis"));
        db.inserirEsportes(new Esporte("Rugby"));
        db.inserirEsportes(new Esporte("Corrida"));
        db.inserirEsportes(new Esporte("Vôlei"));
        db.inserirEsportes(new Esporte("Surf"));
        db.inserirEsportes(new Esporte("Skate"));

        db.cadastrarSoftplayer(new Usuario("Enzo","Klin","enzo.klin@gmail.com","1234",1));
    }

    public void abrirActivityPerfil(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.bottom_home:{
                            selectedFragment = new HomeFragment();
                            toolbarTextView.setText("Softsports");
                            break;
                        }
                        case R.id.bottom_add:{
                            selectedFragment = new NovoEventoFragment();
                            toolbarTextView.setText("Novo evento");
                            break;
                        }
                        case R.id.bottom_list:{
                            selectedFragment = new ListFragment();
                            toolbarTextView.setText("Esportes");
                            break;
                        }
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.navPerfil:{
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.navMeusEventos:{
                Toast.makeText(this, "Eventos", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MeusEventosActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.navConfig:{
                Toast.makeText(this, "Configurações", Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.navSair:{
                finish();
                Intent intent = new Intent(TelaInicialActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void abrirActivityDoEvento(View view) {

        Intent intent = new Intent(this, EventoPerfisActivity.class);
        startActivity(intent);

    }

    public void fecharDrawerLayout(View view){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

}
