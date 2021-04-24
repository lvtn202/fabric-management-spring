package com.example.lvtn.dto;

import com.example.lvtn.dom.ReturnSlip;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnSlipDTO {
    private Long id;

    private String dyehouseName;

    private String money;

    private String receivedName;

    private String returnDate;

    private String firstName;

    private String lastName;

    public ReturnSlipDTO() {
    }

    public ReturnSlipDTO(Long id, String dyehouseName, String money, String receivedName, String returnDate, String firstName, String lastName) {
        this.id = id;
        this.dyehouseName = dyehouseName;
        this.money = money;
        this.receivedName = receivedName;
        this.returnDate = returnDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ReturnSlipDTO{" +
                "id=" + id +
                ", dyehouseName='" + dyehouseName + '\'' +
                ", money='" + money + '\'' +
                ", receivedName='" + receivedName + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    static public ReturnSlipDTO convertReturnSlipToReturnSlipDTO(ReturnSlip returnSlip){
        return new ReturnSlipDTO(
                returnSlip.getId(),
                returnSlip.getDyehouse().getName(),
                String.format("%.3f", returnSlip.getMoney()),
                returnSlip.getReceivedName(),
                String.format("%tQ", returnSlip.getReturnDate()),
                returnSlip.getUser().getFirstName(),
                returnSlip.getUser().getLastName()
        );
    }
}
