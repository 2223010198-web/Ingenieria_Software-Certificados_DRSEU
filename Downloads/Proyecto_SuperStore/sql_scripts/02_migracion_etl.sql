use superStore_DB
go

create procedure usp_MigracionDatosDiagramaEstrella
as
begin
	begin transaction

	begin try
		
		insert into Dim_Clientes(CustomerID, CustomerName, Segment)
        select distinct CustomerID, MAX(CustomerName), MAX(Segment)
        from superStore_registros
        group by CustomerID
        
        insert into Dim_Ubicacion(Country,City,State, PostalCode,Region)
        select distinct Country, City, State, PostalCode, Region
        from superStore_registros
        group by Country, City, State, PostalCode, Region

        insert into Dim_Productos(ProductID, ProductName,Category, SubCategory)
        select distinct ProductID, MAX(ProductName), MAX(Category), MAX(SubCategory)
        from superStore_registros
        group by ProductID

        insert into Fact_Ventas (RowID , OrderID, OrderDate, CustomerID, ProductID, UbicacionID, Sales, Quantity, Discount,Profit,ShipMode,ShipDate, ShippingDays)
        select
            sr.RowID,
            sr.OrderID,
            sr.OrderDate,
            sr.CustomerID,
            sr.ProductID,
            u.UbicacionID,
            sr.Sales,
            sr.Quantity,
            sr.Discount,
            sr.Profit,
            sr.ShipMode ,
             sr.ShipDate,
            sr.ShippingDays
        from superStore_registros sr
        inner join Dim_Ubicacion u on (u.City = sr.City) and (u.PostalCode = sr.PostalCode) and (u.State = sr.State)

        commit transaction
            print 'Se migraron los datos con exito'

	end try

	begin catch
        
        if @@TRANCOUNT > 0
            rollback transaction;
            print 'Hay un error al momento de migrar los datos' + error_message()

	end catch

end
go

exec usp_MigracionDatosDiagramaEstrella
go

/*sp_help 'Fact_Ventas'
sp_help 'superStore_Registros'
select*from Dim_Clientes*/


