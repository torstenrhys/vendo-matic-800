package com.techelevator.view.Exceptions;

import java.text.NumberFormat;

public class InvalidMoneyException extends NumberFormatException{

    public InvalidMoneyException () {
        super();
    }

    public InvalidMoneyException (String message) {
        super(message);
    }

}
