package com.example.onlineshop.ui.Show1OrderDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshop.R
import com.example.onlineshop.data.entity.orderGet.GetOrders
import com.example.onlineshop.databinding.FragmentShowOneOrderBinding


class ShowOneOrderDetrailsFragment : Fragment() {

    private lateinit var fragmentShowOneOrderBinding: FragmentShowOneOrderBinding
    private lateinit var showOrderViewModel:ShowOneOrderDetailsVM
    private lateinit var images : List<String>
    lateinit var order : GetOrders.Order}


/* @SuppressLint("SetTextI18n")
  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
      fragmentShowOneOrderBinding = FragmentShowOneOrderBinding.inflate(
          inflater,
          container,
          false
      )
      val args: ShowOneOrderFragmentArgs by navArgs()

      val viewModelFactory = ViewModelFactory(
          RepositoryImpl(
              RemoteDataSourceImpl(),
              RoomDataSourceImpl(RoomService.getInstance(requireActivity().application))
          ),
          requireActivity().application)

      showOrderViewModel = WeakReference(ViewModelProvider(requireActivity(), viewModelFactory)[ShowOneOrderDetailsVM::class.java]).get()!!



      val ordersListAdapter = WeakReference(OrdersListAdapter(arrayListOf(), arrayListOf())).get()

      fragmentShowOneOrderBinding.imageItemsRecycler.apply {
          this.layoutManager =  LinearLayoutManager(requireContext())
          this.adapter = ordersListAdapter
      }


      fragmentShowOneOrderBinding.progressShowOrderDetails.visibility= View.VISIBLE


      fragmentShowOneOrderBinding.cancelButton.setOnClickListener {
          deleteAlert(showOrderViewModel,order.id!!)
      }

      var total_price : String = ""
      fragmentShowOneOrderBinding.payButton.setOnClickListener {
          startActivity(
              Intent(requireActivity(), CheckoutActivity::class.java).putExtra(
                  "amount",
                  total_price
              )
                  .putExtra("order", order as Serializable)
          )
      }

      showOrderViewModel.observeDeleteOrder().observe(viewLifecycleOwner,{
          if (it){
              view?.findNavController()?.popBackStack()
              showOrderViewModel.observeDeleteOrder().value = false
          }
      })



    changeToolbar()

      showOrderViewModel.getOneOrders(args.productId).observe(viewLifecycleOwner,{

          total_price= it.order?.total_price.toString()
          order = it.order!!

          ordersListAdapter!!.line_items= it.order?.line_items!!


          fragmentShowOneOrderBinding.addressOrder.text = it.order.customer?.default_address!!.city +
                  " ,"+it.order.customer?.default_address.province +" ,"+ it.order.customer?.default_address!!.country

          fragmentShowOneOrderBinding.totalPriceEditable.text = "EGP"+it.order.total_price
          fragmentShowOneOrderBinding.orderIdEditable.text = "# ${it.order.id}"
          val orderDate = it.order.created_at?.split("T")
          val orderTime = orderDate?.get(0)
//            Log.i("output",orderTime!!)
          val time = orderDate?.get(1)?.split("+")
//            Log.i("output",time?.get(0)!!)
          fragmentShowOneOrderBinding.createdAtEditable.text = orderDate?.get(0)
          fragmentShowOneOrderBinding.time.text = time?.get(0)
          copyOrderId.setOnClickListener {
              val clipboard = ContextCompat.getSystemService(requireContext(), ClipboardManager::class.java)
              clipboard?.setPrimaryClip(ClipData.newPlainText("",orderIdEditable.text))
              Toast.makeText(requireContext(),"Copied", Toast.LENGTH_SHORT).show()

          }
          if(it.order.note == "Cash"){
              fragmentShowOneOrderBinding.paymentTypeEditable.text = it.order.note.toString()
              fragmentShowOneOrderBinding.payButton.visibility = View.INVISIBLE
              if (it.order.financial_status == "paid"){
                  fragmentShowOneOrderBinding.tvPay.text = "Paid Order"
                  fragmentShowOneOrderBinding.line1.background = resources.getDrawable(R.drawable.order_update_status_background_shape)
                  fragmentShowOneOrderBinding.cancelButton.visibility = View.GONE
              }else{
                  fragmentShowOneOrderBinding.tvPay.text = resources.getString(R.string.waiting_for_payment)
                  fragmentShowOneOrderBinding.line1.background = resources.getDrawable(R.drawable.state_circle_shape)
                  fragmentShowOneOrderBinding.cancelButton.visibility = View.VISIBLE
              }
          }else{
              fragmentShowOneOrderBinding.paymentTypeEditable.text = "Credit Card"
              if (it.order.financial_status == "paid"){
                  fragmentShowOneOrderBinding.tvPay.text = "Paid Order"
                  fragmentShowOneOrderBinding.line1.background = resources.getDrawable(R.drawable.order_update_status_background_shape)
                  fragmentShowOneOrderBinding.cancelButton.visibility = View.GONE
                  fragmentShowOneOrderBinding.payButton.visibility = View.GONE
              }else{
                  fragmentShowOneOrderBinding.tvPay.text = resources.getString(R.string.waiting_for_payment)
                  fragmentShowOneOrderBinding.line1.background = resources.getDrawable(R.drawable.state_circle_shape)
                  fragmentShowOneOrderBinding.cancelButton.visibility = View.VISIBLE
                  fragmentShowOneOrderBinding.payButton.visibility = View.VISIBLE
              }
          }


          val ids = FilterData.getProductsIDs(it.order)

          showOrderViewModel.getProductAllProuducts().observe(viewLifecycleOwner,{
              images= FilterData.getListOfImageForOneItem(ids,it.products)
              ordersListAdapter.images= images

          })
          fragmentShowOneOrderBinding.progressShowOrderDetails.visibility= View.GONE


      })

      return fragmentShowOneOrderBinding.root
  }


  private fun changeToolbar() {
      requireActivity().findViewById<SearchView>(R.id.mainSearchView).visibility= View.GONE
      requireActivity().findViewById<View>(R.id.nav_view).visibility = View.GONE
      requireActivity().toolbar.visibility = View.VISIBLE
      requireActivity().toolbar.searchIcon.visibility = View.INVISIBLE
      requireActivity().toolbar.settingIcon.visibility = View.INVISIBLE
      requireActivity().findViewById<View>(R.id.favourite).visibility = View.GONE
      requireActivity().findViewById<View>(R.id.cartView).visibility = View.GONE
      requireActivity().toolbar_title.setTextColor(Color.WHITE)

      requireActivity().toolbar.setBackgroundDrawable(ColorDrawable(Color.BLACK))
      requireActivity().toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_24))
      requireActivity().toolbar.setNavigationOnClickListener {
          view?.findNavController()?.popBackStack()

      }

      requireActivity().toolbar_title.text = getString(R.string.my_orders)
  }

  private fun deleteAlert(displayOrderViewModel: ShowOneOrderDetailsVM, order_id: Long) {

      val builder = AlertDialog.Builder(requireContext())
      builder.setTitle(getString(R.string.cancel_order))
      builder.setMessage(getString(R.string.are_you_sure))

      builder.setPositiveButton("Yes") { _, _ ->
          displayOrderViewModel.deleteOrder(order_id)
      }

      builder.setNegativeButton("No") { _, _ ->
      }
      // Create the AlertDialog
      val alertDialog: AlertDialog = builder.create()
      // Set other dialog properties
      alertDialog.setCancelable(false)
      alertDialog.show()
      alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.BLACK)
      alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE)
      alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.DKGRAY)
  }

  override fun onDestroyView() {
      super.onDestroyView()
      fragmentShowOneOrderBinding.progressShowOrderDetails.visibility= View.VISIBLE

}*/