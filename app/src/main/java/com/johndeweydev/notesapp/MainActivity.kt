package com.johndeweydev.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.johndeweydev.notesapp.databinding.ActivityMainBinding
import com.johndeweydev.notesapp.repository.DefaultNotesRepository
import com.johndeweydev.notesapp.viewmodels.DefaultNotesViewModel
import com.johndeweydev.notesapp.viewmodels.NotesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var defaultNotesViewModel: DefaultNotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = DefaultNotesRepository()
        val notesViewModelFactory = NotesViewModelFactory(repository)
        defaultNotesViewModel = ViewModelProvider(
            this, notesViewModelFactory)[DefaultNotesViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentChangeListener()
    }

    private fun fragmentChangeListener()  {
        Navigation.findNavController(this, R.id.fragmentContainerView)
            .addOnDestinationChangedListener{ _, destination, _ ->
                val destLabel = destination.label
                val logText = "fragmentChangeListener: Fragment destination changed to $destLabel"

                Log.d("dev-log", logText)
                countFragmentOnTheStack()
            }
    }

    private fun countFragmentOnTheStack(): Int {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView) as NavHostFragment
        val backStackEntryCount = navHostFragment.childFragmentManager.backStackEntryCount

        val logText = "countFragmentOnTheStack: Current fragment back stack count is" +
                " -> $backStackEntryCount"

        Log.i("dev-log", logText)
        return backStackEntryCount
    }
}