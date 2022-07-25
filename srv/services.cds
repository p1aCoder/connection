    using { ich.connection.management as db } from '../db/ConnectionManagement';
    @path: 'service/risk'
    // Define Books Service
    // service BooksService {
    //     @readonly entity Books as projection   on db.Books { *, category as genre } excluding { category, createdBy, createdAt, modifiedBy, modifiedAt };
    //     @readonly entity Authors as projection on db.Authors;
    // }

    // Define Orders Service
    service OrdersService {
        entity Group as projection on db.Group;
        entity ConnectionDetail as projection on db.ConnectionDetail;
        entity User as projection on db.User;
        entity UserGroupMapping as projection on db.UserGroupMapping;
    }

    // // Reuse Admin Service
    // using { AdminService } from '@sap/capire-products';
    // extend service AdminService with {
    //     entity Authors as projection on db.Authors;
    // }
