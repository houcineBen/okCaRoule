package com.example.okcaroule

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemServiceName
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.okcaroule.Adapter.RecyclerAdapter
import com.example.okcaroule.commandsFragment.dataBase.ElementDatabase
import com.example.okcaroule.commandsFragment.dataBase.ElementsEntity
import com.example.okcaroule.databinding.ActivityMainBinding.inflate
import com.example.okcaroule.databinding.FragmentCommandsFragmentBinding
import java.lang.NumberFormatException


class commandsfragment : Fragment() {
    lateinit var viewModel: CommandsViewModel
    lateinit var binding: FragmentCommandsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_commands_fragment, container,false)
        val prefs = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val dialogView = MaterialDialog(requireContext())
            .noAutoDismiss()
            .customView(R.layout.layout_dialog)
        val app = requireNotNull(this.activity).application
        val database = ElementDatabase.getDatabaseInstance(app).elementDao
        val viewModelFactory = ViewModelFactory(database, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CommandsViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.instance = viewModel
        viewModel._newElement.observe(viewLifecycleOwner, Observer {

            it?.apply {
                //viewModel.dialog(prefs, dialogView)
                viewModel.setEventDone()
                try {
                    val inputService = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    binding.apply {
                        viewModel.closeKeyboard(addBtn, inputService)
                        ElementName = elementName.text.toString()
                        ElementQuantity = elementCount.text.toString().toInt()
                        ElementDescription = elementDiscription.text.toString()
                        elementCount.text = null
                        elementName.text = null
                        elementDiscription.text = null
                    }
                    viewModel.Insert(it, app)
                } catch (e: NumberFormatException) {
                    Toast.makeText(context, "fill in blank fields", Toast.LENGTH_SHORT).show()
                }
            }


        })
        val adapter = RecyclerAdapter(dialogView, viewModel)
        binding.recyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.setElement(it)
        })

        val itemTouchHelper = ItemTouchHelper(simpleCallback(viewModel, adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)



        
        return binding.root
    }
    fun simpleCallback(viewModel: CommandsViewModel, adapter: RecyclerAdapter) = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                viewModel.delete(adapter.getItem(position))
                adapter.notifyItemRemoved(position)
            }
        }

    }




}