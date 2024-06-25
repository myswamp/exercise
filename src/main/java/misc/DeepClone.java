package misc;


public class DeepClone {
    public static void main(String[] args) throws CloneNotSupportedException {
        Book book = new Book();
        Book bookCopy = (Book) book.clone();
        System.out.println(book == bookCopy);
        System.out.println(book.author == bookCopy.author);
        System.out.print(System.lineSeparator());
        DeepCopiedBook deepCopiedBook = new DeepCopiedBook();
        DeepCopiedBook deepCopiedBookCopy = (DeepCopiedBook) deepCopiedBook.clone();
        System.out.println(deepCopiedBook == deepCopiedBookCopy);
        System.out.println(deepCopiedBook.deepCopiedAuthor == bookCopy.deepCopiedAuthor);
        System.out.println(deepCopiedBook.deepCopiedAuthor.name == bookCopy.deepCopiedAuthor.name);

    }
}


class Book implements Cloneable {
    Author author = new Author();
    DeepCopiedAuthor deepCopiedAuthor = new DeepCopiedAuthor();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Author {
    String name = "author";
}

class DeepCopiedBook implements Cloneable {
    DeepCopiedAuthor deepCopiedAuthor = new DeepCopiedAuthor();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepCopiedBook copy = (DeepCopiedBook) super.clone();
        copy.deepCopiedAuthor = (DeepCopiedAuthor) deepCopiedAuthor.clone();
        return super.clone();
    }
}

class DeepCopiedAuthor extends Author implements Cloneable {
    String name = "deep author";

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

