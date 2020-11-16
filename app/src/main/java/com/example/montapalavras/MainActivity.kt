/**
 * A variável 'Data' recebe todas as palavras do "banco"
 *
 * As letras digitadas pelo usuario são aramzenadas na variável 'inputCaracteres' e depois de retirar os espaços e acentos
 * o resultado e armazenado em 'finalCaracteres'
 *
 * A função Validar1 percorre todas as palavras de 'Data' e adiciona em 'palavras' todas as palavras do banco que podem ser formadas
 * com a quantidade de caracteres que o usuario informou
 *
 * A função Validar2 guarda em 'palavrasFinais' as palavras que podem, realmente, ser formadas com os caracteres digitados
 * para isso, percorre caractere por caractere, se necessario, de cada palavra em 'palavras' comparando com os caracteres
 * digitados pelo usuario
 *
 * Se nenhuma palavra foi formada, é informado ao usuario
 * Se uma ou mais palavras forem formadas, 'palavraFinal' recebe a palavra com a maior pontuação considerando a de menor tamanho
 *
 * Para isso, a função PontoPalavra calcula os pontos de todas as palavras que foram formadas e a função MaiorPonto recebe das
 * palavras formadas a maior quantidade de pontos encontrados , apos isso, a pontuação das palavras é comparada e
 * a de maior pontuação e menor tamanho é armazenada em 'palavraFinal'
 *
 * A função Sobraram retorna para 'sobraram' os caracteres, inclusive especiais, não utilizadas para formar a palavra
 *
 * O resultado é informado ao usuário
 */

package com.example.montapalavras

import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.Normalizer
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    var Data: List<String> = listOf<String>("Abacaxi", "Manada", "mandar", "porta", "mesa", "Dado", "Mangas", "Já", "coisas", "radiografia",
            "matemática", "Drogas", "prédios", "implementação", "computador", "balão", "Xícara", "Tédio",
            "faixa", "Livro", "deixar", "superior", "Profissão", "Reunião", "Prédios", "Montanha", "Botânica",
            "Banheiro", "Caixas", "Xingamento", "Infestação", "Cupim", "Premiada", "empanada", "Ratos",
            "Ruído", "Antecedente", "Empresa", "Emissário", "Folga", "Fratura", "Goiaba", "Gratuito",
            "Hídrico", "Homem", "Jantar", "Jogos", "Montagem", "Manual", "Nuvem", "Neve", "Operação",
            "Ontem", "Pato", "Pé", "viagem", "Queijo", "Quarto", "Quintal", "Solto", "rota", "Selva",
            "Tatuagem", "Tigre", "Uva", "Último", "Vitupério", "Voltagem", "Zangado", "Zombaria", "Dor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var text1: TextView = findViewById(R.id.text1)
        var inputField: EditText = findViewById(R.id.inputField)
        val btnOk: Button = findViewById(R.id.buttonOk)
        var pontos: TextView = findViewById(R.id.pontos)
        var sobrou: TextView = findViewById(R.id.sobrou)
        var view: RecyclerView = findViewById(R.id.view)
        var view2: RecyclerView = findViewById(R.id.view2)

        btnOk.setOnClickListener {
            var inputCaracteres = inputField.text.toString()
            var finalCaracteres: String = TiraAcento(inputCaracteres).toUpperCase().replace(" ","")
            var palavras: MutableList<String> = mutableListOf<String>()

            Validar1(finalCaracteres,palavras,Data)

            var palavrasFinais: MutableList<String> = mutableListOf<String>()
            Validar2(finalCaracteres,palavrasFinais,palavras)

            view.layoutManager = GridLayoutManager(this,6)
            view.adapter = TextAdapter()
            view2.layoutManager = GridLayoutManager(this,6)
            view2.adapter = TextAdapter()

            if(palavrasFinais.size>0) {
                var ponto: MutableList<Int> = mutableListOf<Int>()
                var palavraFinal = PalavraMaiorPonto(palavrasFinais, ponto)
                var sobraram: String = Sobraram(finalCaracteres,palavraFinal)
                var texto = "Palavra de " + ponto[0] + " pontos"
                var texto2 = "Sobraram:"
                pontos.text = texto
                sobrou.text = texto2
                text1.text = ""

                val letras = view.adapter
                if(letras is TextAdapter)
                    letras.items = palavraFinal.toList()
                val letras2 = view2.adapter
                if(letras2 is TextAdapter)
                    letras2.items = sobraram.toList()
            }
            else {
                text1.text = "Nenhuma Palavra Formada"
                pontos.text = ""
                sobrou.text = ""

                var texto = ""
                val letras = view.adapter
                if(letras is TextAdapter)
                    letras.items = texto.toList()
                val letras2 = view2.adapter
                if(letras2 is TextAdapter)
                    letras2.items = texto.toList()
            }
        }

    }
}

    private fun TiraAcento(s: String): String {
       val nfdNormalizedString = Normalizer.normalize(s, Normalizer.Form.NFD)
       val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
       return pattern.matcher(nfdNormalizedString).replaceAll("")
    }

    fun Validar1(I: String,L: MutableList<String>,Data: List<String>) {
        for (D in Data){
            if(D.length <= I.length)
                L.add(TiraAcento(D).toUpperCase())
        }
    }

    fun Validar2(I: String,L: MutableList<String>,Words: List<String>){
        for(W in Words){
            var x: Int = 0
            var word: CharArray = I.toCharArray()
            while(x < W.length){
                var y: Int = 0
                while(y < I.length){
                    if(W[x] == I[y]){
                        if(word[y] != '0'){
                            word[y]='0'
                            break
                        }
                    }
                    y++
                }
                if(y==I.length)
                    break
                x++
                if(x == W.length)
                    L.add(W)
            }
        }
    }

    fun PalavraMaiorPonto (Lista: List<String>,ponto: MutableList<Int>): String{
        var pontosPalavras: MutableList<Int> = mutableListOf<Int>()
        for(L in Lista){
            pontosPalavras.add(PontoPalavra(L))
        }
        ponto.add(MaiorPonto(pontosPalavras))
        val p: Int = ponto[0]
        var palavra: String =""

        var x: Int =0
        while (x < pontosPalavras.size){
            if(p == pontosPalavras[x])
                if (palavra.length > Lista[x].length)
                    palavra = Lista[x]
                else if (palavra == "")
                    palavra = Lista[x]
            x++
        }

        return palavra
    }

    fun PontoPalavra(L: String): Int{
        var P: Int =0
        for(C in L){
            when(C){
                'E','A','I','O','N','R','T','L','S','U' -> P++
                'W','D','G' -> P+=2
                'B','C','M','P' -> P+=3
                'F','H','V' -> P+=4
                'J','X' -> P+=8
                'Q','Z' -> P+=10
            }
        }
        return P
    }

    fun MaiorPonto(Pontos: List<Int>): Int{
        var p: Int = 0
        for (P in Pontos){
            if(P > p)
                p = P
        }
        return p
    }

    fun Sobraram(caracteres: String, palavra:String): String{
        var teste: Array<Int> = Array(palavra.length) {i -> 0}
        var sobraram: String =""
        for( C in caracteres){
                var x: Int = 0
                while(x < palavra.length){
                    if(C == palavra[x] && teste[x] == 0){
                        teste[x] = 1
                        break
                    }
                    x++
                }
            if(x == palavra.length)
                sobraram += C
        }
        return sobraram
    }
