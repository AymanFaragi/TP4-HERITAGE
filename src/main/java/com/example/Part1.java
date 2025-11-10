package com.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Part1 {
    public static void main(String[] args) {
        //1
        Publisher publisher=new Publisher();
        publisher.setName("Aymane");
        Author author=new Author();
        author.setName("Faragi");
        author.setEmail("aymane.faragi@gmail.com");


        Category category1=new Category();
        Category category2=new Category();
        category1.setName("Action");
        category2.setName("Horror");

        Book book=new Book();
        book.setTitre("La crime et le chatiment");

        //2
        book.setAuthor(author);
        book.setPublisher(publisher);
        book.setCategories(Arrays.asList(category1,category2));

        //3
        Review review1=new Review();
        Review review2=new Review();
        review1.setComment("Tres bon");
        review1.setBook(book);
        review2.setComment("Mauvais livre");
        review2.setBook(book);

        book.setReviews(Arrays.asList(review1,review2));

        //4
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("book");
        EntityManager em= emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.persist(publisher);
        em.persist(book);
        em.persist(category1);
        em.persist(category2);
        em.persist(review1);
        em.persist(review2);

        em.getTransaction().commit();

        //5
        Book b=em.find(Book.class,book.getId());
        System.out.println("Nom livre : "+b.getTitre());
        System.out.println("Publisher : "+b.getPublisher().getName());
        System.out.println("Categories : "+b.getCategories().stream().map(Category::getName).collect(Collectors.toList()));
        System.out.println("avis : "+b.getReviews().stream().map(Review::getComment).collect(Collectors.toList()));

        em.close();
        emf.close();

        //6


    }
}
