/**
 * Created by platon on 08.03.17.
 */
export class CompanyCreator {
    public email?: string;
    public firstName?: string;
    public lastName?: string;
    public secondName?: string;
    public companyName?: string;
    public phone?: string;
    public secondaryEmail?: string;
    public skype?: string;
    public whatsup?: string;
    public activationKey?: string;

    constructor(
        email?: string,
        firstName?: string,
        lastName?: string,
        secondName?: string,
        companyName?: string,
        phone?: string,
        secondaryEmail?: string,
        skype?: string,
        whatsup?: string,
        activationKey?: string,
    ) {
        this.email = email ? email : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.secondName = secondName ? secondName : null;
        this.companyName = companyName ? companyName : null;
        this.phone = phone ? phone : null;
        this.secondaryEmail = secondaryEmail ? secondaryEmail : null;
        this.skype = skype ? skype : null;
        this.whatsup = whatsup ? whatsup : null;
        this.activationKey = activationKey ? activationKey : null;
    }
}
