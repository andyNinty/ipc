// IBookManger.aidl
package com.example.ipc;
import com.example.ipc.Book;
// Declare any non-default types here with import statements

interface IBookManger {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   List<Book> getBooks();
   void addBook(in Book book);
}
