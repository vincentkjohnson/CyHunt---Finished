package schellekens.restapitest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.HashMap;

import android.widget.EditText;
import android.widget.Toast;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import schellekens.restapitest.ApiIConnector.ApiResultHandler;
import schellekens.restapitest.R.id;
import schellekens.restapitest.models.UserResponse;

/*
@Metadata(
    mv = {1, 1, 13},
    bv = {1, 0, 3},
    k = 1,
    d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u000f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u001c"},
    d2 = {"Lschellekens/restapitest/MainActivity;", "Landroid/support/v7/app/AppCompatActivity;", "Lschellekens/restapitest/ApiIConnector$ApiResultHandler;", "()V", "connector", "Lschellekens/restapitest/ApiIConnector;", "getConnector", "()Lschellekens/restapitest/ApiIConnector;", "view", "Landroid/view/View;", "getView", "()Landroid/view/View;", "setView", "(Landroid/view/View;)V", "handleResult", "", "response", "Lschellekens/restapitest/models/UserResponse;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "app_debug"}
)*/
public class MainActivity extends AppCompatActivity implements ApiResultHandler {

    private View view;
    private final ApiIConnector connector = new ApiIConnector((ApiResultHandler)this);
    private static MainActivity parent;

    public void handleResult(final UserResponse response) {

        if(response != null) {
            parent.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar tBar = (Toolbar)findViewById(id.toolbar);
        parent = this;

        this.setContentView(R.layout.activity_main);
        this.setSupportActionBar(tBar);

        ((FloatingActionButton)this.findViewById(id.fab)).setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View v) {
                view = v;
                EditText txtUser = (EditText)findViewById(id.txtUsername);
                EditText txtPassword = (EditText)findViewById(id.txtPassword);
                connector.LoginUser(txtUser.getText().toString(), txtPassword.getText().toString());
        }}));

        ((FloatingActionButton)this.findViewById(id.fab2)).setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View v) {
                view = v;
                EditText txtUser = (EditText)findViewById(id.txtUsername);
                EditText txtPassword = (EditText)findViewById(id.txtPassword);
                connector.AddUser(txtUser.getText().toString(), txtPassword.getText().toString());
            }
        }));
    }

    public boolean onCreateOptionsMenu(@NotNull Menu menu) {
        Intrinsics.checkParameterIsNotNull(menu, "menu");
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkParameterIsNotNull(item, "item");
        boolean result;
        switch(item.getItemId()) {
            case 2131230742:
            result = true;
            break;
            default:
            result = super.onOptionsItemSelected(item);
        }

        return result;
    }
   }