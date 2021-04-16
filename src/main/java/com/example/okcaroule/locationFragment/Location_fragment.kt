package com.example.okcaroule.locationFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.okcaroule.R
import com.example.okcaroule.commandsFragment.dataBase.ElementDatabase
import com.example.okcaroule.databinding.FragmentLocationFragmentBinding
import com.example.okcaroule.locationFragment.viewModel.ClientViewModelFactory
import com.example.okcaroule.locationFragment.viewModel.LocationFragmentViewModel
import kotlinx.android.synthetic.main.add_client_dialog.*
import kotlinx.android.synthetic.main.fragment_commands_fragment.*
import kotlinx.android.synthetic.main.fragment_location_fragment.*
import kotlinx.android.synthetic.main.location_client_item.*
import java.util.*


class location_fragment : Fragment() {
    lateinit var binding: FragmentLocationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location_fragment, container, false)
        val app = requireNotNull(activity).application
        val db = ElementDatabase.getDatabaseInstance(app).clientDao
        val sharedPrefs = requireActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val viewModelFactory = ClientViewModelFactory(db, sharedPrefs, app)
        val adapter = ClientRecyclerAdapter(requireActivity())
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(LocationFragmentViewModel::class.java)

        binding.ajouterClientBtn.setOnClickListener {
            if (viewModel.availableBikes.value!! > 0) {
                addClientHandler(viewModel)
            } else {
                Toast.makeText(activity, "Plus de velos disponnible!", Toast.LENGTH_LONG).show()
            }
        }

        binding.setLifecycleOwner(this)
        binding.clientViewModel = viewModel
        binding.locationRv.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
            viewModel.calculateBikes()
        })
        viewModel.availableBikes.observe(viewLifecycleOwner, Observer {
            binding.dispoValue.text = it.toString()
        })
        viewModel.totalBikes.observe(viewLifecycleOwner, Observer {
            editor.putInt("totalBikes", it).apply()
            binding.totalValue.text = sharedPrefs.getInt("totalBikes", 0).toString()
        })
        viewModel.bookedBikes.observe(viewLifecycleOwner, Observer {
            editor.putInt("bookedBikes", it).apply()
            binding.louerValue.text = sharedPrefs.getInt("bookedBikes", 0).toString()
        })
        binding.ajouterVelosBtn.setOnLongClickListener {
            viewModel.onAddBikesLongClick()
            viewModel.longClick.observe(viewLifecycleOwner, Observer {
                if (it == 0) {
                    binding.ajouterVelosBtn.setImageResource(R.drawable.ic_baseline_exposure_neg_1_24)
                } else {
                    binding.ajouterVelosBtn.setImageResource(R.drawable.ic_baseline_exposure_plus_1_24)
                }
            })
            true
        }

        val itemTouchHelper = ItemTouchHelper(slideToDelete(viewModel, adapter))
        itemTouchHelper.attachToRecyclerView(binding.locationRv)

        return binding.root
    }

    fun addClientHandler(
        viewModel: LocationFragmentViewModel,

        ) {

        viewModel._newClient.observe(viewLifecycleOwner, Observer { client ->
            MaterialDialog(requireContext()).noAutoDismiss()
                .customView(R.layout.add_client_dialog).show {
                    val adapter = ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.Caution,
                        android.R.layout.simple_spinner_item
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    caution_spinner.adapter = adapter
                    location_duration.setOnClickListener {
                        dateChooser(it as TextView, viewModel)
                    }
                    this.findViewById<Button>(R.id.add_client_btn).setOnClickListener {
                        if (client_name_input.text.isNotEmpty() &&
                            client_phone_input.text.isNotEmpty() &&
                            bikes_count_input.text.isNotEmpty() &&
                            location_duration.text.isNotEmpty()
                        ) {


                            client.apply {

                                clientName =
                                    client_name_input.text.toString()
                                clientPhone =
                                    client_phone_input.text.toString()
                                bikesCount =
                                    bikes_count_input.text.toString()
                                        .toInt()
                                locationDuration =
                                    location_duration.text.toString()
                                price =
                                    price_input.text.toString().toInt()
                                locks =
                                    lock_input.text.toString().toInt()
                                caution =
                                    caution_spinner.selectedItem.toString()

                                if (bikesCount <= viewModel.availableBikes.value!!) {
                                    viewModel.changeBooked("plus", bikesCount)
                                    this@show.dismiss()
                                    viewModel.dialogParser(client)
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Pas Assez de velos!",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                            }
                        } else {
                            Toast.makeText(activity, "il faut tous les champs", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }

        })

    }


    fun dateChooser(input: TextView, viewModel: LocationFragmentViewModel) {
        val datePicker = viewModel.dateDialog()
        datePicker.show(requireFragmentManager(), "String")
        datePicker.addOnPositiveButtonClickListener {
            input.text = datePicker.headerText
        }

    }
    fun slideToDelete(viewModel: LocationFragmentViewModel, adapter: ClientRecyclerAdapter)= object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.RIGHT || direction == ItemTouchHelper.LEFT) {
                viewModel.deleteItem(adapter.getItem(pos))
                viewModel.changeBooked("minus", 1)
                adapter.notifyItemRemoved(pos)

            }
        }
    }
}



