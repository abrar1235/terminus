import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, Observable, retry, throwError } from "rxjs";
import { environment } from "src/environments/environment";

const httpHeaders = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`
    })
};

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private api = environment.api;

    constructor(
        private httpClient: HttpClient
    ) { }

    searchUser(searchTerm: string): Observable<any> {
        return this.httpClient.get<any>(`${this.api}user/getAllUserByKey?key=name&value=${searchTerm}`, httpHeaders).pipe(retry(3), catchError(this.handleError));
    }

    deleteAccoun(userId: string): Observable<any> {
        return this.httpClient.delete<any>(`${this.api}user/deleteUser?userId=${userId}`, httpHeaders).pipe(retry(3), catchError(this.handleError));
    }

    updateUser(user: any): Observable<any> {
        return this.httpClient.put<any>(`${this.api}user/updateUser`, user, httpHeaders)
            .pipe(
                retry(3), catchError(this.handleError)
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