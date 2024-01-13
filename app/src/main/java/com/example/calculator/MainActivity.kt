package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val firstNumberText=StringBuilder("") //변수의 값이 자주 변경되는 경우 StringBuilder 이용
    private val secondNumberText=StringBuilder("")
    private val operatorText=StringBuilder("")
    private val decimalFormat= DecimalFormat("#,###")//세자리마다 ,가 나타나도록 DecimalFormat 설정,08과 같은 수를 8로 변경

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun numberClicked(view: View){  //view: click을 받도록 선언한 view요소(버튼)
        val numberString=(view as? Button)?.text?.toString()?:""//버튼으로 변환, text값 있으면 toString(). 버튼이 아니면 빈 문자열""
        val numberText=if(operatorText.isEmpty()) firstNumberText else secondNumberText //클릭한 버튼가 몇번째 숫자인지:operator뒤에 오면 두번째 숫자

        numberText.append(numberString) //data update
        updateEquationTextView()

    }
    fun clearClicked(view:View){
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()

        updateEquationTextView()
        binding.resultTextView.text=""//resultTextView도 clear해주기
    }
    fun equalClicked(view:View){
        if(firstNumberText.isEmpty()||secondNumberText.isEmpty()||operatorText.isEmpty()){
            Toast.makeText(this,"올바르지 않은 수식입니다.",Toast.LENGTH_SHORT).show()
            return
        }
        val firstNumber=firstNumberText.toString().toBigDecimal()   //int의 표현가능범위를 넘어가면 예외발생;primitive type 대신 BigDecimal 이용
        val secondNumber=secondNumberText.toString().toBigDecimal()    //숫자 두개 각각 받아오기

        val result=when(operatorText.toString()){
            "+"->decimalFormat.format(firstNumber+secondNumber)//format의 반환타입은 문자열
            "-"->decimalFormat.format(firstNumber-secondNumber)
            else->"잘못된 수식입니다."
        }.toString()

        binding.resultTextView.text=result
    }
    fun operatorClicked(view:View){
        val operatorString=(view as? Button)?.text?.toString()?:""
        if(firstNumberText.isEmpty()){
            Toast.makeText(this,"먼저 숫자를 입력해주세요.",Toast.LENGTH_SHORT).show()
            return
        }
        if(secondNumberText.isNotEmpty()){
            Toast.makeText(this,"1개의 연산자에 대해서만 연산이 가능합니다.",Toast.LENGTH_SHORT).show()
            return
        }

        operatorText.append(operatorString) //Update data
        updateEquationTextView()    //Update UI
    }
    private fun updateEquationTextView(){
        val firstFormattedNumber=if(firstNumberText.isNotEmpty())decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""//StringBuilder값을 String으로 변환 후 BigDecimal로 변환
        val secondFormattedNumber=if(secondNumberText.isNotEmpty())decimalFormat.format(secondNumberText.toString().toBigDecimal())else ""

        binding.equationTextView.text="$firstFormattedNumber $operatorText $secondFormattedNumber" //UI upadate
    }
}