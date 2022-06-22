import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class AppUtil {

    setUser(user: any) {
        localStorage.setItem('name', user.name);
        localStorage.setItem('email', user.email);
        localStorage.setItem('profession', user.profession);
        localStorage.setItem('token', user.token);
        localStorage.setItem('id', user.id);
    }

    getValue(key: string) {
        return localStorage.getItem(key);
    }
}