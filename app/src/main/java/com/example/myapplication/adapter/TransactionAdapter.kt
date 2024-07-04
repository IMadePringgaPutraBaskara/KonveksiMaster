package com.example.myapplication.adapter

// TransactionAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Transaction

class TransactionAdapter(private val transactionList: List<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jenisProduk: TextView = view.findViewById(R.id.tvJenisProduk)
        val jumlah: TextView = view.findViewById(R.id.tvJumlah)
        val totalHarga: TextView = view.findViewById(R.id.tvTotalHarga)
        val namaPemesan: TextView = view.findViewById(R.id.tvNamaPemesan)
        val alamat: TextView = view.findViewById(R.id.tvAlamat)
        val tglPesan: TextView = view.findViewById(R.id.tvTglPesan)
        val tglSelesai: TextView = view.findViewById(R.id.tvTglSelesai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]
        holder.jenisProduk.text = transaction.jenis_produk
        holder.jumlah.text = "Jumlah: ${transaction.jumlah}"
        holder.totalHarga.text = "Total Harga: ${transaction.total_harga}"
        holder.namaPemesan.text = "Nama Pemesan: ${transaction.nama_pemesan}"
        holder.alamat.text = "Alamat: ${transaction.alamat}"
        holder.tglPesan.text = "Tanggal Pesan: ${transaction.tgl_pesan}"
        holder.tglSelesai.text = "Tanggal Selesai: ${transaction.tgl_selesai}"
    }

    override fun getItemCount() = transactionList.size
}
