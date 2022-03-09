package com.example.onlineshop.ui.settings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.NavGraphDirections
import com.example.onlineshop.R
import com.example.onlineshop.ViewModelFactory
import com.example.onlineshop.data.remoteDataSource.RemoteDataSourceImpl
import com.example.onlineshop.data.roomData.RoomDataSourceImpl
import com.example.onlineshop.data.roomData.RoomService
import com.example.onlineshop.data.sharedprefrences.MeDataSharedPrefrenceReposatory
import com.example.onlineshop.databinding.FragmentSettingBinding
import com.example.onlineshop.networkBase.NetworkChange
import com.example.onlineshop.repository.RepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var settingsViewModel: SettingViewModel
    private lateinit var meDataSourceReo: MeDataSharedPrefrenceReposatory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeToolbar()
        meDataSourceReo = MeDataSharedPrefrenceReposatory(requireActivity())

        val repository = RepositoryImpl(
            RemoteDataSourceImpl(),
            RoomDataSourceImpl(RoomService.getInstance(requireActivity().application))
        )
        val viewModelFactory = ViewModelFactory(repository, requireActivity().application)
        settingsViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[SettingViewModel::class.java]



        binding.cvDevelopedBy.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToContactFragment())
        }
        binding.cvAddress.setOnClickListener {
            if(meDataSourceReo.loadUsertstate())
                findNavController().navigate(NavGraphDirections.actionGlobalAddressFragment())
            else
                findNavController().navigate( NavGraphDirections.actionGlobalLoginFragment())
        }
        binding.cvProfile.setOnClickListener {
            if(meDataSourceReo.loadUsertstate())
                findNavController().navigate(NavGraphDirections.actionGlobalProfileFragment())
            else
                findNavController().navigate( NavGraphDirections.actionGlobalLoginFragment())
        }

        binding.cvAbout.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToAboutFragment())
        }

     /*   binding.cvFAQ.setOnClickListener {
            findNavController().navigate(SettingFragmentDirections.actionSettingsFragmentToFAQFragment())

        }*/
        if (meDataSourceReo.loadUsertstate()) {
            binding.signout.setOnClickListener {
                if (NetworkChange.isOnline) {
                    meDataSourceReo.saveUsertState(false)
                    meDataSourceReo.saveUsertName("")
                    meDataSourceReo.saveUsertId("")
                    settingsViewModel.clearRoom()
                    findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToMeFragment())
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.thereIsNoNetwork),
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
            binding.signout.visibility=View.VISIBLE
        }else{
            binding.signout.visibility=View.GONE
        }
    }

    fun changeToolbar() {
        requireActivity().findViewById<SearchView>(R.id.mainSearchView).visibility=View.GONE
        requireActivity().findViewById<View>(R.id.nav_view).visibility = View.GONE
        requireActivity().toolbar.visibility = View.VISIBLE
        requireActivity().toolbar.settingIcon.visibility = View.INVISIBLE
        requireActivity().toolbar.searchIcon.visibility = View.INVISIBLE
        requireActivity().toolbar.cartView.visibility = View.INVISIBLE
        requireActivity().toolbar.favourite.visibility = View.INVISIBLE
        requireActivity().toolbar_title.setTextColor(Color.WHITE)

        requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        requireActivity().toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24))
        requireActivity().toolbar.setNavigationOnClickListener {
            view?.findNavController()?.popBackStack()
        }

        requireActivity().toolbar_title.text = "Settings"
    }



}