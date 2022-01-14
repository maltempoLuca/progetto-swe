package com.company.store.shipping.strategy.returnbehavior;

import com.company.store.OperationResult;
import com.company.store.shipping.Shipment;

public final class DoubleReturnBehavior implements ReturnBehavior {
    //deny returning a shipment cause it's a return

    private DoubleReturnBehavior() {

    }

    public static DoubleReturnBehavior getInstance() {
        if (instance == null)
            instance = new DoubleReturnBehavior();
        return instance;
    }

    @Override
    public OperationResult createReturn(Shipment shipment, String userEmail) throws UnsupportedOperationException {
        return  new OperationResult("Impossibile effettuare il reso di un reso", false);
    }

    private static DoubleReturnBehavior instance = null;
}
