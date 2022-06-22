import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, retry, throwError } from "rxjs";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: 'root'
})
export class LoginService {

    private api = environment.api;

    constructor(
        private httpClient: HttpClient
    ) { }

    login(credentials: any): Observable<any> {
        return this.httpClient.post<any>(`${this.api}login`, credentials)
            .pipe(
                retry(3),
                catchError(this.handleError)
            )
    }

    signUp(user: any): Observable<any> {
        return this.httpClient.post<any>(`${this.api}user/addUser`, user)
            .pipe(
                retry(3),
                catchError(this.handleError)
            )
    }

    handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error);
        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            console.error(
                `Backend returned code ${error.status}, body was: `, error.error);
        }
        // Return an observable with a user-facing error message.
        return throwError(() => new Error('Something bad happened; please try again later.'));
    }
}