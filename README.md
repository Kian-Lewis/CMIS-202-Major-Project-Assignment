# CMIS-202-Major-Project-Assignment
Major Project Assignment for my Computer Science 2 class ONL1.

By Kian Lewis


To access the main .java files go to Library of Alexandria/src/application. Alexandria.java is the main class.

LibraryUML is the UML diagram for this program.

<b><h2>Who Are The Users?</h2></b>

  This program was designed to suit the needs of a light reader who has a small collection of books.
  
<b><h2>What Is The Software's Purpose?</h2></b>

  To record and catalog a reasonable sum of physical books into a library, and then sort them by the author's last names. The program would make it simple to see 
  important attributes of a book such as the genre or total page count.

<b><h2>Where And When Will The Software be Used?  </h2></b>

  This program would ideally be used from the user's home computer, and would be when the user has at least a modest amount of time to spare, as recording a large 
  number of books with this software could potentially be fairly laborious.

<b><h2>Why Would Anyone Use The Software Over Existing Processes? </h2></b>

  Honestly speaking, there isn't anything about this program that would beat out existing library programs in terms of ease of use or featuresets. The program does 
  have a small niche appeal of having no ebook shop or links to books that the user does not own, which as far as I am aware is near ubiquitous with library 
  programs, everyone wants to sell you something. The fact that this program does not do this(yet) could be a niche selling point of it for someone who absolutely 
  cannot tolerate advertisement.

<b><h2>Requirement Locations For Part 2</h2></b>
  <b><h3>1. Contain at least one stack, queue, or dequeue structure from the API</h3></b>
    In AddEntry.java the program has been altered so that the user has the option to add more or to save the book to file immediately
    If the user chooses to add another book then the details of the current book are stored in a queue
    When the user chooses to save their books the queue is saved to file all at once.
    Also resets the textfields upon either action.
  <b><h3>2. Use a link list, set, or map structure of your own creation</h3></b>
    Uses sets in ViewLibrary.java in order to remove duplicates from the file when the user presses a button.
    Takes advantage of the fact that a hashset does only allows unique items to do so using the titles of books as the key.
   <b><h3>3. Update GitHub with all changes</h3></b>
    Done.
   <b><h3>4. Participate in any Blackboard activities related to this part</h3></b>
    Hopefully completed as well.

<b><h2>Requirement Locations For Part 1</h2></b>

  <b><h3>1.Read/write to a file</h3></b>
    In AddEntry.java the program writes to a file.
    In ViewLibrary.java the program reads from a file.
  <b><h3>2.Use a JavaFX graphical Interface</h3></b>
    Can be seen in Alexandria.java, AddEntry.java, ViewLibrary.Java, Help.java, and EventManager.java.
  <b><h3>3.Include an array or ArrayList data structure</h3></b>
    ViewLibrary.java involves an ArrayList of type Book, a class based off of Book.java.
  <b><h3>4.Include an efficient sorting algorithm</h3></b>
    ViewLibrary.java implements merge sort, with a fair amount of explanation as to its efficiency.
  <b><h3>5.Use generics and classes with an inheritance structure</h3></b>
    Template.java implements generics, and Book.java inherits from Media.java showcasing a class inheritance structure.
  <b><h3>6.Upload to GitHub</h3></b>
    Hope I did it right.
  <b><h3>7.Participate in any Blackboard activities related to the project</h3></b>
    Will be done.
