
/*Tabla para que se guarden los registros al ejecutarse el trigger*/
create table Auditoria_Nombres(
	AudiID int identity(1,1) primary key,
	ProductID nvarchar(20),
	NombreViejo nvarchar(250),
	NombreNuevo nvarchar(250),
	FechaCambio datetime default getdate(),
	Usuario nvarchar(50) default system_user
)
go

drop table Auditoria_Nombres

use superStore_DB

select*from Dim_Productos
go

/*Trigger cunado se quiere cambiar el nombre del producto*/
create trigger TR_AuditarCambioNombre
on Dim_Productos
for update
as
begin
	
	if UPDATE(ProductName)
	begin
		insert into Auditoria_Nombres(ProductID, NombreViejo, NombreNuevo)
		select
			d.ProductID,
			d.ProductName,
			i.ProductName
		from  deleted d 
		inner join inserted i on d.ProductID = i.ProductID

	end
end


/*Procedimiento almacenado al registrar una venta*/

create procedure usp_RegistrarVenta
	@OrderID nvarchar(20), 
	@OrderDate date, 
	@CustomerID nvarchar(20), 
	@ProductID nvarchar(20), 
	@UbicacionID int, 
	@Sales decimal(10,2), 
	@Quantity int, 
	@Discount decimal(10,2), 
	@Profit decimal(10,2)
as
begin
	begin transaction
	
	begin try
		
		if @Profit < -500
		begin
			;throw 50000, 'Venta Rechazada: Se exceden los limites permitido 500 ',1;
		end
		else
		begin
		insert into Fact_Ventas(RowID,OrderID, OrderDate, CustomerID, ProductID, UbicacionID, Sales, Quantity, Discount, Profit)
		values ((select MAX(RowID) + 1 from Fact_Ventas),@OrderID, @OrderDate, @CustomerID, @ProductID, @UbicacionID, @Sales,@Quantity, @Discount, @Profit)
		end
		commit transaction

	end try

	begin catch
		if @@TRANCOUNT>0
			rollback transaction 
			print 'No se realizo el registro' + error_message()
	end catch

end
go



/*Vista Ranking productos top 5*/

create view vw_RankingProductosPorCategoriaTop5
as
with RankingProducto as (
	select
	p.Category,
	p.ProductName,
	SUM(v.Profit) as TotalProfit,
	DENSE_RANK() over ( partition by p.Category order by SUM(v.Profit) desc) as RankingProdcutos
	from Fact_Ventas v
	inner join Dim_Productos p on v.ProductID = p.ProductID
	group by p.Category,p.ProductName
)
select *from RankingProducto 
where RankingProdcutos  <=5
go

select*from vw_RankingProductosPorCategoria

/*Fucnion para clasificar ventas*/

create function dbo.fn_ClasificarVenta ( 
	@Monto decimal(10,2)
)
returns nvarchar(20)
as
begin
	declare @CategoriaVenta nvarchar(20)

	if @Monto > 5000
	begin
		set @CategoriaVenta = 'Venta Premium'
	end
	else if @Monto >=1000 and @Monto <=5000
	begin
		set @CategoriaVenta = 'Venta Estandar'
	end
	else
	begin 
		set @CategoriaVenta = 'Venta Menor'
	end

	return @CategoriaVenta
end
go

select
p.ProductName,
p.Category,
dbo.fn_ClasificarVenta(Sales) as Clasificacoin
from Fact_Ventas v
inner join Dim_Productos p on p.ProductID=v.ProductID


exec usp_MigracionDatosDiagramaEstrella

