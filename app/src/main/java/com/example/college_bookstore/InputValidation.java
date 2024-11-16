package com.example.college_bookstore;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Switch;

public class InputValidation implements TextWatcher {
    EditText editText;
    String validationtype;
    public InputValidation(EditText editText,String validationtype){
        this.validationtype=validationtype;
        this.editText=editText;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String input = s.toString();

          if(validationtype!=null){
              switch(validationtype){
                  case "price":
                      validateprice( input);
                      break;
                  case "Latter":
                      validateLatter( input);
                      break;
                  default:
                        break;
              }
          }
          else{
              if(s.toString().isEmpty()){
                  editText.setError("Field cannot be empty");
              }
          }
    }

    private void validateprice(String input) {
        if(input.isEmpty()){
          editText.setError("price cannot be empty");
        } else if (!input.matches("^[0-9]+(\\\\.[0-9]{1,2})?$")) {
            editText.setError("Enter Valid Price");
        }
        else{
            editText.setError(null);
        }
    }

    private void validateLatter(String input) {
        if(input.isEmpty()){
            editText.setError("Column cannot be empty");
        } else if (input.length()<3) {
            editText.setError("College name must be at least 3 characters long");
        }
        else{
            editText.setError(null);
        }
    }
}
