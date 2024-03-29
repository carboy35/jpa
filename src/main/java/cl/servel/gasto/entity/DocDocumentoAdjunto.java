package cl.servel.gasto.entity;
// Generated 16-01-2019 18:00:22 by Hibernate Tools 5.2.11.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DocDocumentoAdjunto generated by hbm2java
 */
@Entity
@Table(name = "doc_documento_adjunto")
public class DocDocumentoAdjunto implements java.io.Serializable {

	private Integer docId;
	private RenRendicion renRendicion;
	private String docNombreDocumento;
	private String docIdRepositorioDocumental;
	private String docTipoDocumento;
	private Date docCreated;

	public DocDocumentoAdjunto() {
	}

	public DocDocumentoAdjunto(RenRendicion renRendicion) {
		this.renRendicion = renRendicion;
	}

	public DocDocumentoAdjunto(RenRendicion renRendicion, String docNombreDocumento, String docIdRepositorioDocumental,
			String docTipoDocumento, Date docCreated) {
		this.renRendicion = renRendicion;
		this.docNombreDocumento = docNombreDocumento;
		this.docIdRepositorioDocumental = docIdRepositorioDocumental;
		this.docTipoDocumento = docTipoDocumento;
		this.docCreated = docCreated;
	}

	@SequenceGenerator(name = "DocDocumentoAdjuntoIdGenerator", sequenceName = "doc_documento_adjunto_doc_id_seq",initialValue=1, allocationSize=1)
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "DocDocumentoAdjuntoIdGenerator")

	@Column(name = "doc_id", unique = true, nullable = false)
	public Integer getDocId() {
		return this.docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ren_id", nullable = false)
	public RenRendicion getRenRendicion() {
		return this.renRendicion;
	}

	public void setRenRendicion(RenRendicion renRendicion) {
		this.renRendicion = renRendicion;
	}

	@Column(name = "doc_nombre_documento", length = 1000)
	public String getDocNombreDocumento() {
		return this.docNombreDocumento;
	}

	public void setDocNombreDocumento(String docNombreDocumento) {
		this.docNombreDocumento = docNombreDocumento;
	}

	@Column(name = "doc_id_repositorio_documental", length = 50)
	public String getDocIdRepositorioDocumental() {
		return this.docIdRepositorioDocumental;
	}

	public void setDocIdRepositorioDocumental(String docIdRepositorioDocumental) {
		this.docIdRepositorioDocumental = docIdRepositorioDocumental;
	}

	@Column(name = "doc_tipo_documento", length = 10)
	public String getDocTipoDocumento() {
		return this.docTipoDocumento;
	}

	public void setDocTipoDocumento(String docTipoDocumento) {
		this.docTipoDocumento = docTipoDocumento;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "doc_created", length = 13)
	public Date getDocCreated() {
		return this.docCreated;
	}

	public void setDocCreated(Date docCreated) {
		this.docCreated = docCreated;
	}

}
