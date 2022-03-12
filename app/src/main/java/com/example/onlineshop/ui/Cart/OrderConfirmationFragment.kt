package com.example.onlineshop.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
=======
import  androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.onlineshop.NavGraphDirections
>>>>>>> Stashed changes
import com.example.onlineshop.R

class OrderConfirmationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
<<<<<<< Updated upstream
        return inflater.inflate(R.layout.fragment_order_confirmation, container, false)
=======
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeToolbar()

        if (isLoged()) {
            customerID = meDataSourceReo.loadUsertId()
            Timber.i("olaaa" + customerID)
            if (NetworkChange.isOnline) {
                orderViewModel.getCustomersAddressList(customerID)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.thereIsNoNetwork),
                    Toast.LENGTH_SHORT
                ).show()

            }

            //  orderViewModel.getPriceRulesList()
            // orderViewModel.fetchallDiscountCodeList()
        }

        orderItemsAdapter = OrderItemsAdapter(arrayListOf(), orderViewModel)
        binding.rvCartItems.apply {
            adapter = orderItemsAdapter
        }
        orderViewModel.getAllCartList().observe(viewLifecycleOwner, {
            orderItemsAdapter.addNewList(it)

        })

        orderViewModel.getAddressList().observe(viewLifecycleOwner, Observer<List<Addresse>?> {
            val dafultAddress: MutableList<Addresse> = arrayListOf()
            for (item in it) {
                if (item.default == true) {
                    dafultAddress.add(item)
                    Timber.i("olaada" + item.default)
                }
            }

            if (dafultAddress.isEmpty()) {
                binding.group.visibility = View.INVISIBLE
                binding.addAddressText.visibility = View.VISIBLE
                isDefaultAddress = false
            } else {
                isDefaultAddress = true
                binding.group.visibility = View.VISIBLE
                binding.addAddressText.visibility = View.INVISIBLE
                binding.fullNameTxt.text = dafultAddress.get(0).firstName.toString()
                binding.countryTxt.text = dafultAddress.get(0).country.toString()
                binding.addressTxt.text = dafultAddress.get(0).address1.toString()
            }

        })
        binding.addressBtn.setOnClickListener {
            val action = NavGraphDirections.actionGlobalAddressFragment()
            findNavController().navigate(action)
        }

        binding.discountBtn.setOnClickListener {
            binding.discountEdt.visibility = View.VISIBLE
            binding.discountBtn.visibility = View.INVISIBLE
            binding.discountBtnHide.visibility = View.VISIBLE
        }
        binding.discountBtnHide.setOnClickListener {
            discount_edt.visibility = View.GONE
            binding.discountBtnHide.visibility = View.INVISIBLE
            binding.discountBtn.visibility = View.VISIBLE
        }
        binding.placeOrderBtn.setOnClickListener {
            //edit on adress
            if (true) {
                binding.placeOrderBtn.startAnimation();

                //[do some async task. When it finishes]
                //You can choose the color and the image after the loading is finished
                var bitmap: Bitmap? =
                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_check_24);
                if (bitmap != null) {
                    binding.placeOrderBtn.doneLoadingAnimation(Color.WHITE, bitmap)
                }

//[or just revert de animation]
                //  binding.placeOrderBtn.revertAnimation();
                placeOrder()

            } else {
                Toast.makeText(context, "please, set your address", Toast.LENGTH_SHORT).show()
            }
        }
        orderViewModel.getPostOrder().observe(viewLifecycleOwner, Observer<OneOrderResponce?> {
            binding.placeOrderBtn.stopAnimation()
            if (it != null) {
                // clearBackStack()
                Timber.i("ordemm+" + it.order)
                Timber.i("orderrr+" + it.order)
                orderViewModel.delAllItems()
//                val action = NavGraphDirections.actionGlobalShopTabFragment2()
//                findNavController().navigate(action)
                it?.let {
                    if (isCash) {
//                        val action = NavGraphDirections.actionGlobalShopTabFragment2()
//                        findNavController().navigate(action)
                        view.findNavController()
                            .navigate(OrderConfirmationFragmentDirections.actionOrderConfirmationFragmentToShopTabFragment2())
                        //  view?.findNavController()?.popBackStack()
                    } else {
                        // Navigation.findNavController(view).navigate(R.id.Checkout_Activity)
                        startActivity(
                            Intent(requireActivity(), CheckoutActivity::class.java).putExtra(
                                "amount",
                                totalPrice.toString()
                            )

                                .putExtra("order", it.order as? Serializable)
                        )
                    }
                }

            } else {
                Toast.makeText(context, "error Try again please", Toast.LENGTH_SHORT).show()
            }
        })
//        orderViewModel.getPriceRules().observe(viewLifecycleOwner, Observer<List<PriceRule>?> {
//            priceRulesList=it
//
//        })

        orderViewModel.fetchallDiscountCodeList().observe(viewLifecycleOwner, Observer<AllCodes> {
        //    discountCodesList = it.discountCodes

        })
        binding.discountEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Timber.i("olaa  afterTextChanged")
                checkDiscountCode()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Timber.i("olaa  beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Timber.i("olaa  onTextChanged")
            }
        })

        binding.totalPriceTxt.text = totalPrice.toString() + " EGP"
        binding.totalItemTxt.text = totalPrice.toString() + " EGP"

    }

    //    private fun checkDiscountCode() {
//        val discount: List<PriceRule> =
//            priceRulesList.filter {
//                it.title == binding.discountEdt.text.toString().trim()
//            }
//        if (discount.isEmpty()) {
//            Toast.makeText(context, "Sorry, this coupon is invalid", Toast.LENGTH_SHORT).show()
//        } else {
//            binding.totalDiscountTxt.text = discount.get(0).value+"%"
//            totalDiscont= discount.get(0).value?.toFloat() ?:0.0f
//            Timber.i("olaaa discountAmount"+discountAmount+"  "+totalDiscont+"  "+totalPrice)
//            discountAmount = (totalPrice*totalDiscont)/100
//            binding.totalPriceTxt.text=(totalPrice+totalDiscont).toString() + " EGP"
//
//
//        }
//    }
    private fun checkDiscountCode() {
        val discount: List<DiscountCode> =
            discountCodesList.filter {
                Timber.i("olaaa code " + it.code)
                it.code == binding.discountEdt.text.toString().trim()
            }
        if (discount.isEmpty()) {
            isDiscount = false
            binding.discountEdt.setError("Sorry, this coupon is invalid")
            binding.totalDiscountTxt.text = "---"
            binding.totalPriceTxt.text = (totalPrice).toString() + " EGP"
            // Toast.makeText(context, "Sorry, this coupon is invalid", Toast.LENGTH_SHORT).show()
        } else {
            isDiscount = true
            binding.totalDiscountTxt.text = "10%"
            discountAmount = ((totalPrice * 10) / 100)
            discountCode = discount.get(0).code.toString()
            Timber.i("olaaa discountAmount" + discountAmount + "  " + totalDiscont + "  " + totalPrice)
            totalPrice = (totalPrice - discountAmount)
            binding.totalPriceTxt.text = totalPrice.toString() + " EGP"

        }
    }


    private fun placeOrder() {
        var customerOrder = CustomerOrder(customerID.toLong())
        var lineItem: MutableList<LineItem> = arrayListOf()
        var discount: MutableList<DiscountCodes>? = arrayListOf()
        val items = orderItemsAdapter.orderList.map {
            it.variants?.get(0)
        }

        for (item in items) {
            lineItem.add(LineItem(item?.inventory_quantity, item?.id))
        }
        getPaymentMethod()
        if (isDiscount)
            discount?.add(DiscountCodes(discountAmount.toString(), discountCode))
        else
            discount = null

        var order = Order(customerOrder, "pending", lineItem, paymentMethod, discount)
        var orders = Orders(order)
        if (NetworkChange.isOnline) {
            orderViewModel.createOrder(orders)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.thereIsNoNetwork),
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun getPaymentMethod() {
        if (binding.radioCash.isChecked) {
            paymentMethod = "Cash"
            isCash = true
        } else if (binding.radioCredit.isChecked) {
            paymentMethod = "Card"
            isCash = false
        }

>>>>>>> Stashed changes
    }


}