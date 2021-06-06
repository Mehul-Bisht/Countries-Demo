package com.example.countries.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.countries.R;
import com.example.countries.data.database.CountryMapper;
import com.example.countries.data.network.Country;
import com.example.countries.databinding.ActivityMainBinding;
import com.example.countries.util.Resource;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private CountryAdapter countryAdapter;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        countryAdapter = new CountryAdapter(this);
        binding.recyclerview.setAdapter(countryAdapter);

        dialog = new AlertDialog.Builder(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel.getData(this).observe(this, new Observer<Resource<List<CountryMapper>>>() {

            @Override
            public void onChanged(Resource<List<CountryMapper>> data) {

                if (data instanceof Resource.Loading) {

                    binding.progressBar.setVisibility(View.VISIBLE);
                }

                if (data instanceof Resource.Success) {

                    binding.progressBar.setVisibility(View.GONE);

                    List<CountryMapper> list = (List<CountryMapper>) ((Resource.Success<List<CountryMapper>>) data).getData();

                    countryAdapter.differ.submitList(list);
                }

                if (data instanceof Resource.Error) {

                    binding.progressBar.setVisibility(View.GONE);

                    Snackbar.make(
                            binding.recyclerview,
                            (String) ((Resource.Error<List<CountryMapper>>) data).getMessage(),
                            Snackbar.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item != null) {

            if (item.getItemId() == R.id.delete) {

                if (dialog != null) {

                    dialog.setTitle("Delete items")
                            .setMessage("Are you sure you want to delete all the items?")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    viewModel.delete();
                                    Snackbar.make(binding.recyclerview,"Deleted all items",Snackbar.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                }
            }

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}