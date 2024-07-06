package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Transaction

class AdminTransactionAdapter(
    private val transactionList: ArrayList<Transaction>
) : RecyclerView.Adapter<AdminTransactionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jenisProduk: TextView = itemView.findViewById(R.id.jenisProduk)
        val jumlah: TextView = itemView.findViewById(R.id.jumlah)
        val totalHarga: TextView = itemView.findViewById(R.id.totalHarga)
        val namaPemesan: TextView = itemView.findViewById(R.id.namaPemesan)
        val alamat: TextView = itemView.findViewById(R.id.alamat)
        val tglPesan: TextView = itemView.findViewById(R.id.tglPesan)
        val tglSelesai: TextView = itemView.findViewById(R.id.tglSelesai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction_admin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.jenisProduk.text = transaction.jenis_produk
        holder.jumlah.text = transaction.jumlah.toString()
        holder.totalHarga.text = transaction.total_harga.toString()
        holder.namaPemesan.text = transaction.nama_pemesan
        holder.alamat.text = transaction.alamat
        holder.tglPesan.text = transaction.tgl_pesan
        holder.tglSelesai.text = transaction.tgl_selesai
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }
}
