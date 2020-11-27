package alura.com.projetoveiculo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var bd:SQLiteDatabase //bd é um atributo da classe MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        abrirFecharBanco()
        adaptarLista()
    }
    fun abrirFecharBanco(){
        bd = openOrCreateDatabase("veiculo.db", Context.MODE_PRIVATE, null)
        bd.execSQL("CREATE TABLE IF NOT EXISTS tblVeiculo(placa varchar(65) primary key not null, ano int(4) not null, marca varchar(255) not null, modelo varchar(255) not null, motor float(5) not null)")
    }

    fun salvar(view: View) {
        val placa = findViewById<EditText>(R.id.EditTextPlaca)
        val ano = findViewById<EditText>(R.id.EditTextAno)
        val marca = findViewById<EditText>(R.id.EditTextMarca)
        val modelo = findViewById<EditText>(R.id.EditTextModelo)
        val motor = findViewById<EditText>(R.id.EditTextMotor)

        val coluna = ContentValues()
        coluna.put("placa", placa.text.toString())
        coluna.put("ano", ano.text.toString())
        coluna.put("marca", marca.text.toString())
        coluna.put("modelo", modelo.text.toString())
        coluna.put("motor", motor.text.toString())

        //inserir
        bd.insert("tblVeiculo", null, coluna)
        Toast.makeText(this, "Veículo salvo com sucesso", Toast.LENGTH_LONG).show()
        adaptarLista()
    }

    fun adaptarLista() {
        val adaptar = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listar())
        val listaVeiculos = findViewById<ListView>(R.id.listarVeiculos)
        listaVeiculos.adapter = adaptar
    }

    fun listar(): ArrayList<String> {
        val cursor = bd.rawQuery("SELECT * FROM tblVeiculo order by placa", null, null)

        //criar um vetor para retornar a lista
        val lista = ArrayList<String>()
        //percorrer linha a linha e add o dado na lista
        while (cursor.moveToNext()){
            lista.add(cursor.getString(cursor.getColumnIndex("placa")))
        }
        return lista
    }

    fun limpar(view: View){
        val placa = findViewById<EditText>(R.id.EditTextPlaca)
        val ano = findViewById<EditText>(R.id.EditTextAno)
        val marca = findViewById<EditText>(R.id.EditTextMarca)
        val modelo = findViewById<EditText>(R.id.EditTextModelo)
        val motor = findViewById<EditText>(R.id.EditTextMotor)
        placa.requestFocus()

        placa.setText("")
        ano.setText("")
        marca.setText("")
        modelo.setText("")
        motor.setText("")
    }
}