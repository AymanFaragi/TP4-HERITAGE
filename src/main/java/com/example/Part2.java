package com.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Part2 {
    public static void main(String[] args) {
        //1

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("book");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Book> query1 = em.createQuery(
                "select b from Book b join b.categories c where c.name = :categoryName",
                Book.class
        );
        query1.setParameter("categoryName", "Action");
        List<Book> booksAction = query1.getResultList();
        for (Book b : booksAction) {
            System.out.println( b.getTitre());
        }
        System.out.println();

        //2

        TypedQuery<Book> query2 = em.createQuery(
                "select b from Book b where b.publisher.name = :publisherName",
                Book.class
        );
        query2.setParameter("publisherName", "Aymane");
        List<Book> booksPublisher = query2.getResultList();
        for (Book b : booksPublisher) {
            System.out.println( b.getTitre()+" publie par :"+b.getPublisher().getName());
        }
        System.out.println();

/*
        //3
        em.getTransaction().begin();

        Book bookToDelete = em.find(Book.class, 252L);
        if (bookToDelete != null) {
            System.out.println("Suppression du livre: " + bookToDelete.getTitre());
            em.remove(bookToDelete);
        }
        em.getTransaction().commit();
*/
        //4

        em.getTransaction().begin();
        Book nvBook = em.find(Book.class, 302);
        if (nvBook != null) {
            nvBook.setPrice(19.37);

            System.out.println("Livre: " + nvBook.getTitre());
            System.out.println("Nouveau prix: " + nvBook.getPrice());

            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
            System.out.println("Livre non trouve!");
        }

       //5
        Book bookTest = em.find(Book.class, 302);
        System.out.println("Livre charge: " + bookTest.getTitre());

        System.out.println("Reviews (EAGER): " + bookTest.getReviews().size());
        System.out.println("Publisher (LAZY): " + bookTest.getPublisher().getName());
        System.out.println("Categories (LAZY): " + bookTest.getCategories().size());



        emf.close();
    }
}
