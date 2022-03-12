package com.example.onlineshop.ui.ProductDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
=======
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
>>>>>>> Stashed changes
import com.example.onlineshop.R


class ProductDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

<<<<<<< Updated upstream
=======
    private fun setStoredButton(stored: Boolean) {
        if (stored) {
            bindingProductDetailsFragment.addToWishList.setImageResource(R.drawable.favorite)
        } else {
            bindingProductDetailsFragment.addToWishList.setImageResource(R.drawable.ic__favorite_border_24)
        }
    }


    private fun createAlertToSignIn(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(getString(R.string.you_are_not_signed))
        alertDialogBuilder.setMessage(getString(R.string.sign_in_and_try_again))
        alertDialogBuilder.setPositiveButton(getString(R.string.ok)) { _, _ ->
        }
        alertDialogBuilder.setNegativeButton(getString(R.string.signin)) { _, _ ->
//            val intent = Intent(context, SignIn::class.java)
//            startActivity(intent)
        }
        alertDialogBuilder.show()
    }


    private fun changeToolbar() {
        requireActivity().findViewById<SearchView>(R.id.mainSearchView).visibility = View.GONE

        requireActivity().findViewById<View>(R.id.nav_view).visibility = View.GONE
        requireActivity().toolbar.visibility = View.VISIBLE
        if (requireActivity().findViewById<View>(R.id.searchIcon) != null) {
            requireActivity().findViewById<View>(R.id.searchIcon).visibility = View.INVISIBLE
            requireActivity().findViewById<View>(R.id.settingIcon).visibility = View.INVISIBLE

            requireActivity().findViewById<View>(R.id.favourite).visibility = View.INVISIBLE
            requireActivity().findViewById<View>(R.id.cartView).visibility = View.VISIBLE

            requireActivity().toolbar_title.setTextColor(Color.BLACK)
            requireActivity().toolbar_title.text = ""
        }


        requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        requireActivity().toolbar.navigationIcon = ContextCompat.getDrawable(requireActivity(),R.drawable.black_arrow)
        requireActivity().toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        productDetailsViewMode.progressPar.value = false
        productDetailsViewMode.optionsMutableLiveData.value = -1


    }

    private fun isLoged() = meDataSourceReo.loadUsertstate()


>>>>>>> Stashed changes
}
