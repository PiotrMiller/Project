package classes;

/**
 * Created by Piotr on 13.03.2018.
 */
public class ProductType {

        public ProductType(String typeName) {
            this.typeName = typeName;
        }

        private int typeId;
        private String typeName;

        public void setProductTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getProductTypeId() {
            return typeId;
        }

        public String getProductTypeName() {
            return typeName;
        }



}
