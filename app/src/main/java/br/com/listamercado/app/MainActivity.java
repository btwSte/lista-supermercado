package br.com.listamercado.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list_view;
    EditText txtProduto;
    ProdutoAdapter adapter;


    View.OnClickListener click_ck = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            CheckBox ck = (CheckBox) view;

            int posicao = (int) ck.getTag();

            Produto produtoSelecionado = adapter.getItem(posicao);

            Produto produtodb = Produto.findById(Produto.class, produtoSelecionado.getId());

            if (ck.isChecked()) {
                produtodb.setAtivo(true);
                produtodb.save();
                produtoSelecionado.setAtivo(true);
            } else {
                produtodb.setAtivo(false);
                produtodb.save();
                produtoSelecionado.setAtivo(false);
            }

            adapter.notifyDataSetChanged();


        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_view);
        txtProduto = (EditText) findViewById(R.id.txtProduto);

        List<Produto> lstProdutos = Produto.listAll(Produto.class);

        //cria adapter
        adapter = new ProdutoAdapter(this, lstProdutos);

        //liga o adapter a list_view
        list_view.setAdapter(adapter);
    }

    public void inserirItem(View view) {
        String produto = txtProduto.getText().toString();

        //só ira adicionar se tiver conteudo
        if (produto.isEmpty()){
            return;
        }

        //criar obj produto
        Produto p = new Produto(produto, false);

        //adicionando na lista
        adapter.add(p);

        //add no banco
        p.save();

        //limpa caixa de texto
        txtProduto.setText(null);
    }

    //Classe do Adapter
    private class ProdutoAdapter extends ArrayAdapter<Produto>{
        public ProdutoAdapter(Context ctx, List<Produto> itens){
            super(ctx, 0, itens);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            if (v == null){
                v = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, null);
            }

            Produto item = getItem(position);

            TextView txtItemProduto = v.findViewById(R.id.txtItemProduto);
            CheckBox ckItemProduto = v.findViewById(R.id.ckItemProduto);

            txtItemProduto.setText(item.getNome());
            ckItemProduto.setChecked(item.isAtivo());

            //Guarda a posição do item
            ckItemProduto.setTag(position);


            ckItemProduto.setOnClickListener(click_ck);

            return v;
        }
    }
}
